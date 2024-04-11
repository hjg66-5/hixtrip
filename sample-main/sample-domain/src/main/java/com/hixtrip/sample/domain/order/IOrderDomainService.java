package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.SampleCart;

public interface IOrderDomainService {


    /**
     * 通过购物车下单
     * @param sampleCart
     * @return
     * @throws Exception
     */
    CommandPay createOrder(SampleCart sampleCart) throws Exception;


    /**
     * 更新订单状态
     * @param orderId
     */
    void orderPaySuccess(String orderId);




}
