package com.jason.common.vo;

import lombok.Data;

import com.jason.common.bo.BaseUserBO;

@Data
public class BaseUserVO {
	
	private BaseUserBO baseUserBO;
	
	private String validTimeStr;
}
