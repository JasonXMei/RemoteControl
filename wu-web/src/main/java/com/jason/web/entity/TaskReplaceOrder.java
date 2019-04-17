package com.jason.web.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class TaskReplaceOrder extends Model<TaskReplaceOrder> {

    private static final long serialVersionUID = 1L;

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
    private Integer orderType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
