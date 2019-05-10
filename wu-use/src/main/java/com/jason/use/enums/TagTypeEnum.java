package com.jason.use.enums;

public enum TagTypeEnum {

	Collect(0, "收藏"), PlusPurchase(1, "加购"), Auctioned(2, "已拍"),
	Chat(3, "聊天"), FootPrint(4, "足迹");
	
	private int tagType;
	private String description;
	
	TagTypeEnum(int type, String description) {
		this.tagType = type;
		this.description = description;
	}

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
