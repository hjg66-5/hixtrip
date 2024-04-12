package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.PayOrderDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrderDo> {

    int insert(PayOrderDo order);

    PayOrderDo queryUnPayOrder(PayOrderDo order);

    void updateOrderPayInfo(PayOrderDo order);

    void changeOrderPaySuccess(PayOrderDo order);

    String querySkuIdByOrderId(@Param("orderId") String orderId);

}
