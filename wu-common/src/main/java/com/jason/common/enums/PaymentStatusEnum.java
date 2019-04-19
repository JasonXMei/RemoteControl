package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum {
	
	Unpaid(1, "未支付"), Paid(2, "已支付"), Refunded(3, "已返款");
	
	@EnumValue
	private int status;
	private String description;
	
	private PaymentStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
	
	
}
