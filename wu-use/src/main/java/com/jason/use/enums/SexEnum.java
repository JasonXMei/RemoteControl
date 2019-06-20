package com.jason.use.enums;

import java.util.ArrayList;
import java.util.List;

public enum SexEnum {

	Unchoose(-1, "请选择用户性别"), Man(0, "男"), Woman(1, "女");

	public int status;
	public String description;

	SexEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

    public static int getPaymentStatus(String description){
        for(SexEnum e : SexEnum.values()){
            if(e.description.equals(description)){
                return e.status;
            }
        }
        return -1;
    }

    public static List<String> getAllStatus() {
        List<String> list = new ArrayList<>();
        for(SexEnum e : SexEnum.values()){
            list.add(e.description);
        }
        return list;
    }
}
