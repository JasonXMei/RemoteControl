package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum PermissionEnum {
	
	SuperAdmin(0, "超级管理员"), NormalUser(1, "普通智慧联盟账号");
	
	@EnumValue
	private int type;
	private String description;
	
	PermissionEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}

    public static PermissionEnum getType(Integer type){
        for(PermissionEnum e : PermissionEnum.values()){
            if(e.getType() == type){
                return e;
            }
        }
        return null;
    }
}
