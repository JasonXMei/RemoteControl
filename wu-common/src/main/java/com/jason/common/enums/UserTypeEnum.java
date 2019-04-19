package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
	TaoBao(1, "淘宝"), JingDong(2, "京东"), PingDuoDuo(3, "拼多多");
	
	@EnumValue
	private int type;
	private String description;
	
	private UserTypeEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}
}
