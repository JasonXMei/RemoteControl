package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum TagTypeEnum {

	Collect(0, "收藏"), PlusPurchase(1, "加购"), Auctioned(2, "已拍"),
	Chat(3, "聊天"), FootPrint(4, "足迹");
	
	@EnumValue
	private int tagType;
	private String description;
	
	TagTypeEnum(int type, String description) {
		this.tagType = type;
		this.description = description;
	}
}
