package com.jason.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.enums.HttpStatus;
import com.jason.common.enums.PaymentStatusEnum;
import com.jason.common.po.SubUserTask;
import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.util.BeanUtil;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskReplaceOrderVO;
import com.jason.web.mapper.SubUserTaskMapper;
import com.jason.web.mapper.TaskReplaceOrderMapper;
import com.jason.web.service.TaskReplaceOrderService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Service
@Slf4j
public class TaskReplaceOrderServiceImpl extends ServiceImpl<TaskReplaceOrderMapper, TaskReplaceOrder> implements TaskReplaceOrderService {

    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private SubUserTaskMapper subUserTaskMapper;

    @Override
    public TaskPage<TaskReplaceOrderVO> handleList(TaskPage<TaskReplaceOrder> pages) {
        pages = BeanUtil.updateTaskPagesParams(pages, log);
        pages = baseMapper.findReplaceOrderList(pages);

        TaskPage<TaskReplaceOrderVO> pagesVO = new TaskPage<>();
        List<TaskReplaceOrderVO> list = new ArrayList<>();
        for (TaskReplaceOrder tro : pages.getRecords()) {
            list.add(BeanUtil.convertReplaceOrderPO2VO(dozerMapper, tro));
        }

        pagesVO.setRecords(list);
        BeanUtil.convertTaskPages(pagesVO, pages.getTotal(), pages.getSize(), pages.getCurrent(), pages.getSearchName(),
            pages.getSearchStatus(), pages.getSearchStartDateStr(),pages.getSearchEndDateStr());
        return pagesVO;
    }

    @Override
    public JSONResult<String> handleOrder(Integer orderId, Integer status) {
        TaskReplaceOrder tro = new TaskReplaceOrder();
        tro.setId(orderId);
        tro.setPaymentStatus(PaymentStatusEnum.getEnum(status));
        baseMapper.updateById(tro);

        SubUserTask subUserTask = new SubUserTask();
        subUserTask.setId(tro.getTaskId());
        subUserTask.setUpdateTime(Calendar.getInstance().getTimeInMillis());
        subUserTaskMapper.updateById(subUserTask);
        return new JSONResult<>(null, HttpStatus.OK.status, String.format(HttpStatus.OK.getMessage(), "更新补单"));
    }
}
