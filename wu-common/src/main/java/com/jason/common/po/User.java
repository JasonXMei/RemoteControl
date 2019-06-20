package com.jason.common.po;

import com.jason.common.enums.ConnectStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jason.common.enums.AccountStatusEnum;
import com.jason.common.enums.PermissionEnum;
import com.jason.common.enums.SexEnum;

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
public class User{

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 位置
     */
    private String location;

    /**
     * 信用值
     */
    private Integer creditValues;

    /**
     * 付款码图片路径
     */
    private String paymentCodeImg;

    /**
     * QQ号
     */
    private String qqNumber;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 权限
     */
    private PermissionEnum permission;

    /**
     * 账号状态
     */
    private AccountStatusEnum status;

    /**
     * 账号每日刷单上限次数
     */
    private Integer allowOrderTimes;

    /**
     * 有效时间
     */
    private Long validTime;

    /**
     * 使用端连接状态
     */
    private ConnectStatusEnum connectStatusUse;
    /**
     * 客户端连接状态
     */
    private ConnectStatusEnum connectStatusClient;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 推荐人id
     */
    private Integer referrerUserId;
}
