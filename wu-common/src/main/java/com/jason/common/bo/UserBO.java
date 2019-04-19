package com.jason.common.bo;

import java.util.List;

import lombok.Data;

import com.jason.common.po.SubUser;
import com.jason.common.po.UserShop;

@Data
public class UserBO {

	private BaseUserBO baseUserBO;

    /**
     * 有效时间
     */
    private Integer validTime;
    
    /**
     * 账号关联小号
     */
    private List<SubUser> subUserList;
    
    /**
     * 账号关联店铺
     */
    private List<UserShop> shopList;
 }
