package com.jason.common.vo;

import lombok.Data;

import com.jason.common.bo.BaseTaskBO;
import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.po.TaskTag;

@Data
public class TaskTagVO {
	
	private BaseTaskBO baseTaskBO;
	
	private String createTimeStr;

	private String updateTimeStr;
	
	/**
     * 标签任务
     */
    private TaskTag taskTag;
    
    /**
     * 补单任务
     */
    private TaskReplaceOrder taskReplaceOrder;
}
