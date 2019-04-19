package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum AccountStatusEnum {
	
	Normal(1, "正常"), Forbidden(2, "禁用"), Delete(3, "删除");
	
	@EnumValue
	private int status;
	private String description;
	
	private AccountStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
	
	
}
