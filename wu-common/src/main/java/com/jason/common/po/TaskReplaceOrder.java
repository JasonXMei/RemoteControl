package com.jason.common.po;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jason.common.enums.OrderTypeEnum;
import com.jason.common.enums.PaymentStatusEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskReplaceOrder{

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 逻辑关联sub_user_task表id
     */
    private Integer taskId;

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

    /**
     * 订单类型(1：电脑单，2：手机单....)
     */
    private OrderTypeEnum orderType;
    
    /**
     * 垫付状态(1：未垫付，2：已垫付，3：已返款)
     */
    private PaymentStatusEnum paymentStatus;
}
