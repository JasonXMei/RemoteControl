package com.jason.common.bo;

import lombok.Data;

import com.jason.common.po.SubUser;
import com.jason.common.po.UserShop;

@Data
public class BaseTaskBO {
	
	/**
     * 主键
     */
    private Integer id;

    /**
     * 备注描述
     */
    private String description;
    
    /**
     * 小号
     */
    private SubUser subUser;
	
    /**
     * 店铺
     */
	private UserShop userShop;
}
