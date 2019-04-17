package com.jason.web.entity;

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
public class SubUserTask extends Model<SubUserTask> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 逻辑关联sub_user表id
     */
    private Integer subUserId;

    /**
     * 逻辑关联user_shop表id
     */
    private Integer shopId;

    /**
     * 垫付状态(1：未垫付，2：已垫付，3：已返款)
     */
    private Boolean paymentStatus;

    /**
     * 备注描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 更新时间
     */
    private Integer updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
