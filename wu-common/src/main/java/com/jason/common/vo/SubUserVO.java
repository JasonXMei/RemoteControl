package com.jason.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SubUserVO {

	/**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 逻辑关联user表id
     */
    private Integer userId;

    /**
     * 小号名
     */
    private String subUserName;

    /**
     * 终端类型
     */
    private String terminalStr;

    /**
     * 小号类型
     */
    private String userTypeStr;

    /**
     * 连接状态
     */
    private String connectStatusStr;

    /**
     * 年龄
     */
    private Integer age;

    private String userName;
    private String orderTimes;
    private String location;
    private String sexStr;
    private String desc;
}
