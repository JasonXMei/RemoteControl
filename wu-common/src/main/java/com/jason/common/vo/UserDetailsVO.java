package com.jason.common.vo;

import java.util.List;

import lombok.Data;

import com.jason.common.po.SubUser;
import com.jason.common.po.UserShop;

@Data
public class UserDetailsVO {
	
	private BaseUserVO baseUserVO;
	
	/**
     * 账号关联小号
     */
    private List<SubUser> subUserList;
    
    /**
     * 账号关联店铺
     */
    private List<UserShop> shopList;
	
}
