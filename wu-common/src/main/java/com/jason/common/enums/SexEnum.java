package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum SexEnum {
	
	Man(1, "男"), Woman(2, "女");
	
	@EnumValue
	private int type;
	private String description;
	
	private SexEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}
}
