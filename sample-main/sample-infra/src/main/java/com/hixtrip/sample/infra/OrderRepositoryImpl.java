package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import com.hixtrip.sample.domain.valobj.OrderStatusVO;
import com.hixtrip.sample.infra.db.dataobject.PayOrderDo;
import com.hixtrip.sample.infra.db.mapper.PayOrderMapper;

import javax.annotation.Resource;

public class OrderRepositoryImpl implements OrderRepository {

    @Resource
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
    public Sample queryProductByProductId(String productId) {
        return null;
    }

    @Override
    public void updateOrderPayInfo(CommandPay payOrderEntity) {

    }

    @Override
    public void changeOrderPaySuccess(String orderId) {

    }
}
