package com.jason.client.enums;

import java.util.ArrayList;
import java.util.List;

public enum PaymentStatusEnum {
	
	Unchoose(-1, "请选择订单状态"), Unpaid(0, "未支付"), Paid(1, "已支付"), Refunded(2, "已返款");
	
	public int status;
	public String description;
	
	PaymentStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}

    public static int getPaymentStatus(String description){
        for(PaymentStatusEnum e : PaymentStatusEnum.values()){
            if(e.description.equals(description)){
                return e.status;
            }
        }
        return -1;
    }

    public static List<String> getAllStatus() {
        List<String> list = new ArrayList<>();
        for(PaymentStatusEnum e : PaymentStatusEnum.values()){
            list.add(e.description);
        }
        return list;
    }
}
