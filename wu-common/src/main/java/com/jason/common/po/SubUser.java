package com.jason.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jason.common.enums.ConnectStatusEnum;
import com.jason.common.enums.SexEnum;
import com.jason.common.enums.TerminalEnum;
import com.jason.common.enums.UserTypeEnum;
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
public class SubUser {

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
    private TerminalEnum terminal;

    /**
     * 小号类型
     */
    private UserTypeEnum userType;

    /**
     * 连接状态
     */
    private ConnectStatusEnum connectStatus;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 年龄
     */
    private Integer age;

    /*private String userName;
    private Integer allowOrderTimes;
    private Integer orderTimes;
    private String location;*/
}
