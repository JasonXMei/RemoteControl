package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum OrderTypeEnum {
	
	ComputerOrder(0, "电脑单"), MobileOrder(1, "手机单");
	
	@EnumValue
	private int orderType;
	private String description;
	
	OrderTypeEnum(int type, String description) {
		this.orderType = type;
		this.description = description;
	}
}
