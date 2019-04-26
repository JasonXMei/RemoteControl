package com.jason.common.vo;

import com.jason.common.po.SubUser;
import com.jason.common.po.UserShop;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsVO {
	
	private UserVO userVO;
	
	/**
     * 账号关联小号
     */
    private List<SubUser> subUserList;
    private int subUserCount;
    
    /**
     * 账号关联店铺
     */
    private List<UserShop> shopList;
    private int shopCount;
}
