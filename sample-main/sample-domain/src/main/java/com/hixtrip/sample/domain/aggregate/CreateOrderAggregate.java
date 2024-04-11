package com.hixtrip.sample.domain.aggregate;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.sample.model.SampleCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /**
     * 用户ID
     * */
    private String userId;
    /**
     *
     * */
    private SampleCart sampleCart;

    private Order orderEntity;

}
