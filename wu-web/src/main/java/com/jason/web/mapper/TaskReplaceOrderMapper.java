package com.jason.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.vo.TaskPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface TaskReplaceOrderMapper extends BaseMapper<TaskReplaceOrder> {

    TaskPage<TaskReplaceOrder> findReplaceOrderList(@Param("pg")TaskPage<TaskReplaceOrder> pages);

    TaskPage<TaskReplaceOrder> findReplaceOrderListById(@Param("pg")TaskPage<TaskReplaceOrder> pages);

    Integer findOrderCount(@Param("userId") Integer userId, @Param("status") Integer status);
}
