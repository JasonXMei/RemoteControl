package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum SexEnum {
	
	Man(0, "男"), Woman(1, "女");
	
	@EnumValue
	private int type;
	private String description;
	
	SexEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static SexEnum getSexEnumByType(Integer type){
		for(SexEnum sexEnum : SexEnum.values()){
			if(sexEnum.getType() == type){
				return sexEnum;
			}
		}
		return null;
	}
}
