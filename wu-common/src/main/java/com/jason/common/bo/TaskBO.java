package com.jason.common.bo;

import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.po.TaskTag;

import lombok.Data;

@Data
public class TaskBO {
	
	private BaseTaskBO baseTaskBO;
	
	/**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 更新时间
     */
    private Integer updateTime;
    
    /**
     * 标签任务
     */
    private TaskTag taskTag;
    
    /**
     * 补单任务
     */
    private TaskReplaceOrder taskReplaceOrder;
}
