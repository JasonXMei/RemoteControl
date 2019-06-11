package com.jason.use.enums;

public enum ConnectStatusEnum {
	
	ToBeConnect(0, "可以连接"),Connected(1, "被连接"),DisConnected(2, "未上线");
	
	public int status;
	public String description;
	
	ConnectStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
}
