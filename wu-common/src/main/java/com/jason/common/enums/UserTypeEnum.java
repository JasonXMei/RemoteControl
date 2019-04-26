package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
	TaoBao(0, "淘宝"), JingDong(1, "京东"), PingDuoDuo(2, "拼多多");
	
	@EnumValue
	private int type;
	private String description;
	
	UserTypeEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}
}
