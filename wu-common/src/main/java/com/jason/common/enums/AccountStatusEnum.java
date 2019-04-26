package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum AccountStatusEnum {
	
	Normal(0, "正常"), Forbidden(1, "禁用"), Delete(2, "删除"), WaitAudit(3, "待审核");
	
	@EnumValue
	private int status;
	private String description;
	
	AccountStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public static AccountStatusEnum getEnum (Integer status){
		for(AccountStatusEnum e : AccountStatusEnum.values()){
			if(e.getStatus() == status){
				return e;
			}
		}
		return null;
	}
}
