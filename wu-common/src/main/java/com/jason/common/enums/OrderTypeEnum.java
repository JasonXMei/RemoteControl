package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum OrderTypeEnum {
	
	ComputerOrder(1, "电脑单"), MobileOrder(2, "手机单");
	
	@EnumValue
	private int orderType;
	private String description;
	
	private OrderTypeEnum(int type, String description) {
		this.orderType = type;
		this.description = description;
	}
}
