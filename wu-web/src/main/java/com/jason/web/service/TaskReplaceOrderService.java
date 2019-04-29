package com.jason.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskReplaceOrderVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface TaskReplaceOrderService extends IService<TaskReplaceOrder> {

    TaskPage<TaskReplaceOrderVO> handleList(TaskPage<TaskReplaceOrder> pages);

    JSONResult<String> handleOrder(Integer orderId, Integer status);
}
