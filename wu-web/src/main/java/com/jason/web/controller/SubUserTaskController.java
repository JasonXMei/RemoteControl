package com.jason.web.controller;

import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskVO;
import com.jason.web.service.TaskReplaceOrderService;
import com.jason.web.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Controller
@RequestMapping("/client/task/")
public class SubUserTaskController {

	@Autowired
	private TaskReplaceOrderService taskReplaceOrderService;
	@Autowired
	private TaskTagService taskTagService;

    @RequestMapping("saveReplaceOrder")
    @ResponseBody
    public JSONResult<String> saveReplaceOrder(TaskVO taskVO){
        return taskReplaceOrderService.saveReplaceOrder(taskVO);
    }

    @RequestMapping("saveTag")
    @ResponseBody
    public JSONResult<String> saveTag(TaskVO taskVO){
        return taskTagService.saveTag(taskVO);
    }
}
