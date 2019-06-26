package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum NeedClientLoginEnum {

	Need(0, "需要"), NotNeed(1, "不需要");

	@EnumValue
	private int type;
	private String description;

	NeedClientLoginEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static NeedClientLoginEnum getEnumByType(Integer type){
		for(NeedClientLoginEnum e : NeedClientLoginEnum.values()){
			if(e.getType() == type){
				return e;
			}
		}
		return null;
	}
}
