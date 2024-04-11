package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService implements IOrderDomainService{


    /**
     * todo 需要实现
     * 创建待付款订单
     *
     * @return
     */
    public CommandPay createOrder(SampleCart sampleCart) {
        //需要你在infra实现, 自行定义出入参
        return null;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }
}
