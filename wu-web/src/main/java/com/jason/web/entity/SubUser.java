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
public class SubUser extends Model<SubUser> {

    private static final long serialVersionUID = 1L;

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
     * 终端类型(1：电脑端，2：手机端....)
     */
    private Integer terminal;

    /**
     * 小号类型(1:淘宝，2：京东，3：拼多多...)
     */
    private Integer userType;

    /**
     * 连接状态(1:未连接，2：已连接)
     */
    private Integer connectStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
