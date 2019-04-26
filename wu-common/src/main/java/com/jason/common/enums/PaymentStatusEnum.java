package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum {
	
	Unpaid(0, "未支付"), Paid(1, "已支付"), Refunded(2, "已返款");
	
	@EnumValue
	private int status;
	private String description;
	
	PaymentStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
}
