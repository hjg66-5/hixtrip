package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.aggregate.CreateOrderAggregate;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import com.hixtrip.sample.domain.valobj.OrderStatusVO;
import com.hixtrip.sample.infra.db.dataobject.PayOrderDo;
import com.hixtrip.sample.infra.db.mapper.PayOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private PayOrderMapper payOrderMapper;


    @Override
    public Order queryUnPayOrder(SampleCart sampleCart) {
        // 1. 封装参数
        PayOrderDo orderReq = new PayOrderDo();
        orderReq.setUserId(sampleCart.getUserId());
        orderReq.setSkuId(sampleCart.getSkuId());
        // 2. 查询到订单
        PayOrderDo payOrderDo = payOrderMapper.queryUnPayOrder(orderReq);
        if (null == payOrderDo) return null;
        // 3. 返回结果
        return Order.builder()
                .skuId(payOrderDo.getSkuId())
                .productName(payOrderDo.getProductName())
                .id(payOrderDo.getOrderId())
                .payStatus(OrderStatusVO.valueOf(payOrderDo.getStatus()))
                .createTime(payOrderDo.getOrderTime())
                .money(payOrderDo.getTotalAmount())
                .payUrl(payOrderDo.getPayUrl())
                .build();
    }

    @Override
    public Sample queryProductByProductId(SampleCart sampleCart) {
        // 实际场景要从从数据库查询
        return Sample.builder()
                .id(Long.valueOf(sampleCart.getSkuId()))
                .name("小米VIP-SU7-文旅")
                .desc("666")
                .price(new BigDecimal("1688"))
                .build();
    }

    @Override
    public void doSaveOrder(CreateOrderAggregate orderAggregate) {
        String userId = orderAggregate.getUserId();
        SampleCart sampleCart = orderAggregate.getSampleCart();
        Order orderEntity = orderAggregate.getOrderEntity();

        PayOrderDo order = new PayOrderDo();
        order.setUserId(userId);
        order.setSkuId(sampleCart.getSkuId());
        order.setProductName(sampleCart.getSkuId());
        order.setOrderId(orderEntity.getId());
        order.setOrderTime(orderEntity.getCreateTime());
        order.setTotalAmount(BigDecimal.valueOf(sampleCart.getAmount()));
        order.setStatus(orderEntity.getPayStatus().getCode());

        payOrderMapper.insert(order);
    }

    @Override
    public void updateOrderPayInfo(CommandPay payOrderEntity) {
        PayOrderDo order = new PayOrderDo();
        order.setUserId(payOrderEntity.getUserId());
        order.setOrderId(payOrderEntity.getOrderId());
        order.setPayUrl(payOrderEntity.getPayUrl());
        order.setStatus(payOrderEntity.getPayStatus().getCode());
        payOrderMapper.updateOrderPayInfo(order);
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        PayOrderDo order = new PayOrderDo();
        order.setOrderId(orderId);
        order.setStatus(OrderStatusVO.PAY_SUCCESS.getCode());
        payOrderMapper.changeOrderPaySuccess(order);
    }
}
