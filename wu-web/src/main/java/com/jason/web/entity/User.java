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
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

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
     * 性别（1：男，2：女）
     */
    private Integer sex;

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
    private Integer permission;

    /**
     * 账号状态(1：正常，2：禁用，3：删除)
     */
    private Integer status;

    /**
     * 账号每日刷单上限次数
     */
    private Integer allowOrderTimes;

    /**
     * 有效时间
     */
    private Integer validTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
