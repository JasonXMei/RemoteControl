package com.jason.common.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskVO {

    /**
     * 淘宝订单id
     */
    private String orderId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单回扣
     */
    private BigDecimal orderCommission;

    private Integer userId;

    private Integer shopId;

    /**
     * 标签类型
     */
    private String tagTypeStr;

    /**
     * 映射字段
     */
    private String description;
}
