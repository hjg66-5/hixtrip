package com.hixtrip.sample.domain.order;


import com.alipay.api.AlipayApiException;
import com.hixtrip.sample.domain.aggregate.CreateOrderAggregate;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import com.hixtrip.sample.domain.valobj.OrderStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
public abstract class AbstractOrderDomainService implements IOrderDomainService{

    @Autowired
    protected OrderRepository orderRepository;

    /**
     * 模板方法
     * @param sampleCart
     * @return
     * @throws Exception
     */
    @Override
    public CommandPay createOrder(SampleCart sampleCart) throws Exception {
        // 1. 查询当前用户是否存在掉单和未支付订单
        Order unpaidOrderEntity = orderRepository.queryUnPayOrder(sampleCart);
        if (null != unpaidOrderEntity && OrderStatusVO.PAY_WAIT.equals(unpaidOrderEntity.getPayStatus())) {
            log.info("创建订单-存在，已存在未支付订单。userId:{} skuId:{} orderId:{}", sampleCart.getUserId(), sampleCart.getSkuId(), unpaidOrderEntity.getId());
            return CommandPay.builder()
                    .orderId(unpaidOrderEntity.getId())
                    .payUrl(unpaidOrderEntity.getPayUrl())
                    .build();
        } else if (null != unpaidOrderEntity && OrderStatusVO.CREATE.equals(unpaidOrderEntity.getPayStatus())) {
            log.info("创建订单-存在，存在未创建支付单订单，创建支付单开始 userId:{} skuId:{} orderId:{}", sampleCart.getUserId(), sampleCart.getSkuId(), unpaidOrderEntity.getId());
            CommandPay payOrderEntity = this.doPrepayOrder(sampleCart.getUserId(), sampleCart.getSkuId(), unpaidOrderEntity.getProductName(), unpaidOrderEntity.getId(), unpaidOrderEntity.getMoney(),sampleCart.getAmount());
            return CommandPay.builder()
                    .orderId(payOrderEntity.getOrderId())
                    .payUrl(payOrderEntity.getPayUrl())
                    .build();
        }

        // 2. 查询商品 & 聚合订单
        Sample sample = orderRepository.queryProductByProductId(sampleCart);
        Order orderEntity = Order.builder()
                .skuId(String.valueOf(sample.getId()))
                .productName(sample.getName())
                .id(RandomStringUtils.randomNumeric(16))
                .createTime(new Date())
                .payStatus(OrderStatusVO.CREATE)
                .build();
        CreateOrderAggregate orderAggregate = CreateOrderAggregate.builder()
                .userId(sampleCart.getUserId())
                .sampleCart(sampleCart)
                .orderEntity(orderEntity)
                .build();

        // 3. 保存订单 - 保存一份订单，再用订单生成ID生成支付单信息
        this.doSaveOrder(orderAggregate);


        // 4. 创建支付单
        CommandPay commandPay = this.doPrepayOrder(sampleCart.getUserId(), sampleCart.getSkuId(), sample.getName(), orderEntity.getId(), sample.getPrice(),sampleCart.getAmount());
        log.info("创建订单-完成，生成支付单。userId: {} orderId: {} payUrl: {}", sampleCart.getUserId(), orderEntity.getId(), commandPay.getPayUrl());

        return commandPay.builder()
                .orderId(commandPay.getOrderId())
                .payUrl(commandPay.getPayUrl())
                .build();
    }


    /**
     * 保存订单
     *
     * @param orderAggregate 订单聚合
     */
    protected abstract void doSaveOrder(CreateOrderAggregate orderAggregate);

    /**
     * 预支付订单生成
     *
     * @param userId      用户ID
     * @param productId   商品ID
     * @param productName 商品名称
     * @param orderId     订单ID
     * @param totalAmount 支付金额
     * @return 预支付订单
     */
    protected abstract CommandPay doPrepayOrder(String userId, String productId, String productName, String orderId, BigDecimal totalAmount,Integer amount) throws AlipayApiException;

}
