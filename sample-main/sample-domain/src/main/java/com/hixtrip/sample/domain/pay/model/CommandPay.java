package com.hixtrip.sample.domain.pay.model;

import com.hixtrip.sample.domain.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {


    /**
     * 用户ID
     * */
    private String userId;

    /**
     * 支付地址；创建支付后，获得支付信息；
     * */
    private String payUrl;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态 0-创建完成、1-等待支付、2-支付成功、3-交易完成、4-订单关单
     */
    private OrderStatusVO payStatus;


    @Override
    public String toString() {
        return "CommandPay{" +
                "userId='" + userId + '\'' +
                ", payUrl='" + payUrl + '\'' +
                ", orderId='" + orderId + '\'' +
                ", payStatus=" + payStatus +
                '}';
    }
}