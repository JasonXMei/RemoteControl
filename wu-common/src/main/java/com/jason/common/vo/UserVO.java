package com.jason.common.vo;

import lombok.Data;

@Data
public class UserVO {

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
	 * 性别（0：男，1：女）
	 */
	private String sexStr;

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
	private Integer permissionInt;

	/**
	 * 账号状态(0：正常，1：禁用，2：删除,3：待审核)
	 */
	private String statusStr;

	/**
	 * 账号每日刷单上限次数
	 */
	private Integer allowOrderTimes;

	/**
	 * 账户有效时间
	 */
	private String validTimeStr;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 推荐人id
     */
    private Integer referrerUserId;

    /**
     * 推荐人邀请码
     */
    private String referrerUserInviteCode;
}
