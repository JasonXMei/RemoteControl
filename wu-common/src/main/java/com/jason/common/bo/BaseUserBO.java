package com.jason.common.bo;

import lombok.Data;

import com.jason.common.enums.AccountStatusEnum;
import com.jason.common.enums.PermissionEnum;
import com.jason.common.enums.SexEnum;

@Data
public class BaseUserBO {
	/**
     * 主键
     */
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
     * 性别（1：男，2：女）
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
     * 权限(0：超级管理员，1：普通智慧联盟账号)
     */
    private PermissionEnum permission;

    /**
     * 账号状态(1：正常，2：禁用，3：删除)
     */
    private AccountStatusEnum status;

    /**
     * 账号每日刷单上限次数
     */
    private Integer allowOrderTimes;
}
