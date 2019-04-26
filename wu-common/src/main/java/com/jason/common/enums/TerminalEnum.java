package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum TerminalEnum {
	Computer(0, "电脑"), Mobile(1, "手机");
	
	@EnumValue
	private int terminalType;
	private String description;
	
	TerminalEnum(int terminalType, String description) {
		this.terminalType = terminalType;
		this.description = description;
	}
}
