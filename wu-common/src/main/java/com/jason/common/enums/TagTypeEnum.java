package com.jason.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

@Getter
public enum TagTypeEnum {

	Collect(1, "收藏"), PlusPurchase(2, "加购"), Auctioned(3, "已拍"),
	Chat(4, "聊天"), FootPrint(5, "足迹");
	
	@EnumValue
	private int tagType;
	private String description;
	
	private TagTypeEnum(int type, String description) {
		this.tagType = type;
		this.description = description;
	}
}
