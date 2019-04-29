package com.jason.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskReplaceOrderVO {

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
     * 垫付状态
     */
    private String paymentStatusStr;

    /**
     * 映射字段
     */
    private String description;
    private String subUserName;
    private String QQNumber;
    private String userName;
    private String paymentCodeImg;
    private String shopName;
	private String createTimeStr;
}
