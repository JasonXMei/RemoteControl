package com.jason.common.vo;

import lombok.Data;

import com.jason.common.bo.BaseTaskBO;
import com.jason.common.po.TaskReplaceOrder;

@Data
public class TaskReplaceOrderVO {
	
	private BaseTaskBO baseTaskBO;
	
	private String createTimeStr;

	private String updateTimeStr;
	
    /**
     * 补单任务
     */
    private TaskReplaceOrder taskReplaceOrder;
}
