package com.jason.common.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class SubUserTask{

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
}
