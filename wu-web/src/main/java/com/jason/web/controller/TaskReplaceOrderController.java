package com.jason.web.controller;

import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.po.User;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskReplaceOrderVO;
import com.jason.web.service.TaskReplaceOrderService;
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
@RequestMapping("/replaceOrder")
public class TaskReplaceOrderController {

    @Autowired
    private TaskReplaceOrderService taskReplaceOrderService;

	@RequestMapping("/list")
	public String taskList(TaskPage<TaskReplaceOrder> pages, HttpServletRequest request){
        User loginUser = (User)request.getAttribute("user");
        pages.setSearchUserId(loginUser.getId());
        TaskPage<TaskReplaceOrderVO> list = taskReplaceOrderService.handleList(pages);
        request.setAttribute("replaceOrderList", list);
        return "task/replacOrderList";
	}

    /**
     * 更改订单状态
     * @param status 状态(0：未支付，1：已支付，2：已返款)
     */
    @PostMapping("/handle/{orderId}/{status}/")
    @ResponseBody
    public JSONResult<String> handleUser (@PathVariable("orderId") Integer orderId,
                                  @PathVariable("status") Integer status){
        return taskReplaceOrderService.handleOrder(orderId, status);
    }
}
