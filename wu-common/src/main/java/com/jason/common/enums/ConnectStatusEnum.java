package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum ConnectStatusEnum {
	
	Connected(1, "已连接"),DisConnect(2, "未连接");
	
	@EnumValue
	private int status;
	private String description;
	
	private ConnectStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
	
	
}
