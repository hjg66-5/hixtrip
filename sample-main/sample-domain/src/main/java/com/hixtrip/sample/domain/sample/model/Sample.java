package com.hixtrip.sample.domain.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * 这是领域对象的示例, 要求使用充血模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Sample {
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 商品描述
     * */
    private String desc;

    /**
     * 商品价格
     */
    private BigDecimal price;

}
