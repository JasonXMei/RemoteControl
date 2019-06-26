package com.jason.use.enums;

public enum NeedClientLoginEnum {

	Need(0, "需要"), NotNeed(1, "不需要");

	public int type;
	public String description;

	NeedClientLoginEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static NeedClientLoginEnum getSexEnumByType(Integer type){
		for(NeedClientLoginEnum e : NeedClientLoginEnum.values()){
			if(e.type == type){
				return e;
			}
		}
		return null;
	}
}
