package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum ConnectStatusEnum {
	
	ToBeConnect(0, "可以连接"),Connected(1, "被连接"),DisConnected(2, "未上线");
	
	@EnumValue
	private int status;
	private String description;
	
	ConnectStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
}
