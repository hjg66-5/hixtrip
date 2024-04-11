package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.SampleCart;

public interface IOrderDomainService {



    CommandPay createOrder(SampleCart sampleCart) throws Exception;



    void orderPaySuccess(CommandPay commandPay);




}
