package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractOrderDomainService implements IOrderDomainService{


    @Override
    public CommandPay createOrder(SampleCart sampleCart) throws Exception {
        // 1. 查询当前用户是否存在掉单和未支付订单

        // 2. 查询商品 & 聚合订单


        // 3. 保存订单 - 保存一份订单，再用订单生成ID生成支付单信息



        // 4. 创建支付单


        return null;
    }
}
