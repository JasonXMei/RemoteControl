package com.jason.web.controller;

import com.jason.common.enums.HttpStatus;
import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskReplaceOrderVO;
import com.jason.common.vo.TaskVO;
import com.jason.web.service.TaskReplaceOrderService;
import com.jason.web.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("orderList")
    @ResponseBody
    public JSONResult<TaskPage<TaskReplaceOrderVO>> taskList(TaskPage<TaskReplaceOrder> pages, HttpServletRequest request){
        TaskPage<TaskReplaceOrderVO> list = taskReplaceOrderService.handleListClient(pages);
        JSONResult<TaskPage<TaskReplaceOrderVO>> jsonResult = new JSONResult<>(list, HttpStatus.OK.status, null);
        return jsonResult;
    }

    @RequestMapping("orderCount/{status}/{userId}")
    @ResponseBody
    public Integer orderCount(@PathVariable("status")Integer status,
                              @PathVariable("userId")Integer userId){
        return taskReplaceOrderService.getOrderCount(userId, status);
    }

    /**
     * 更改订单状态
     * @param status 状态(0：未支付，1：已支付，2：已返款)
     */
    @PostMapping("handle/{orderId}/{status}/")
    @ResponseBody
    public JSONResult<String> handleUser (@PathVariable("orderId") Integer orderId,
                                          @PathVariable("status") Integer status){
        return taskReplaceOrderService.handleOrder(orderId, status);
    }
}
