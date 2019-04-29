package com.jason.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TaskTagVO {

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
    private String tagTypeStr;

    /**
     * 映射字段
     */
    private String description;
    private String subUserName;
    private String QQNumber;
    private String userName;
    private String shopName;
    private String createTimeStr;
}
