package com.hixtrip.sample.domain.order;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hixtrip.sample.domain.aggregate.CreateOrderAggregate;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import com.hixtrip.sample.domain.valobj.OrderStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Service
public class OrderDomainService extends AbstractOrderDomainService{

    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Value("${alipay.return_url}")
    private String returnUrl;
    @Autowired
    private AlipayClient alipayClient;

    @Override
    protected void doSaveOrder(CreateOrderAggregate orderAggregate) {
        orderRepository.doSaveOrder(orderAggregate);
    }

    @Override
    protected CommandPay doPrepayOrder(String userId, String productId, String productName,
                                       String orderId, BigDecimal totalAmount,Integer amount) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();

        CommandPay commandPay = new CommandPay();
        commandPay.setOrderId(orderId);
        commandPay.setPayUrl(form);
        commandPay.setPayStatus(OrderStatusVO.PAY_WAIT);

        // 更新订单支付信息
        orderRepository.updateOrderPayInfo(commandPay);

        return commandPay;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(String orderId) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.changeOrderPaySuccess(orderId);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }
}
