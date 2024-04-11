package com.hixtrip.sample.entry;

import com.hixtrip.sample.client.enums.ResponseCode;
import com.hixtrip.sample.client.model.Response;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.IOrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * todo 这是你要实现的
 */
@Slf4j
@RestController
public class OrderController {

    @Resource
    private IOrderDomainService orderService;


    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public Response<String> order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        //登录信息可以在这里模拟
        var userId = commandOderCreateDTO.getUserId();
        var skuId = commandOderCreateDTO.getSkuId();
        var amount = commandOderCreateDTO.getAmount();
        try {
            log.info("商品下单，根据商品ID创建支付单开始 userId:{} skuId:{} amount:{}", userId,skuId,amount);
            SampleCart shopCartEntity = SampleCart.builder().userId(userId).skuId(skuId).amount(amount).build();
            CommandPay payOrderEntity = orderService.createOrder(shopCartEntity);
            log.info("商品下单，根据商品ID创建支付单完成 userId:{} skuId:{}  amount:{} orderId:{}", userId, skuId,amount, payOrderEntity.getOrderId());
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(payOrderEntity.getPayUrl())
                    .build();
        } catch (Exception e) {
            log.error("商品下单，根据商品ID创建支付单失败 userId:{} skuId:{}", userId, skuId, e);
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        return "";
    }

}
