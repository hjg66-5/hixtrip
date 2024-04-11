package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.model.SampleCart;

/**
 *
 */
public interface OrderRepository {

    /**
     * 查询未支付订单
     *
     * @param sampleCart 购物车实体对象
     * @return 订单实体对象
     */
    Order queryUnPayOrder(SampleCart sampleCart);

    /**
     * 模拟查询商品信息
     *
     * @param productId 商品ID
     * @return 商品实体对象
     */
    Sample queryProductByProductId(String productId);


    /**
     * 更新订单支付信息
     *
     * @param payOrderEntity 支付单
     */
    void updateOrderPayInfo(CommandPay payOrderEntity);

    /**
     * 订单支付成功
     * @param orderId 订单ID
     */
    void changeOrderPaySuccess(String orderId);



}
