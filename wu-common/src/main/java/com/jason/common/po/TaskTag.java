package com.jason.common.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jason.common.enums.TagTypeEnum;

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
public class TaskTag{

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
     * 标签类型
     */
    private TagTypeEnum tagType;

    /**
     * 映射字段
     */
    private String description;
    private String subUserName;
    private String QQNumber;
    private String userName;
    private String shopName;
    private Long createTime;
}
