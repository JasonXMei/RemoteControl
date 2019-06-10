package com.jason.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.enums.HttpStatus;
import com.jason.common.enums.PaymentStatusEnum;
import com.jason.common.po.SubUserExt;
import com.jason.common.po.SubUserTask;
import com.jason.common.po.TaskReplaceOrder;
import com.jason.common.util.BeanUtil;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskReplaceOrderVO;
import com.jason.common.vo.TaskVO;
import com.jason.web.mapper.SubUserMapper;
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
    @Autowired
    private SubUserMapper subUserMapper;

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
    public TaskPage<TaskReplaceOrderVO> handleListClient(TaskPage<TaskReplaceOrder> pages) {
        pages = BeanUtil.updateTaskPagesParams(pages, log);
        pages = baseMapper.findReplaceOrderListById(pages);

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

    @Override
    public JSONResult<String> saveReplaceOrder(TaskVO taskVO) {
        long currentDateTimeStamp = BeanUtil.getCurrentTimeStamp(log);
        SubUserExt subUserExt = subUserMapper.findSubUser(taskVO.getUserId(), currentDateTimeStamp);
        if(subUserExt.getAllowOrderTimes() <= subUserExt.getOrderTimes()){
            return new JSONResult<>(null, HttpStatus.ERROR.status, String.format(HttpStatus.ERROR.getMessage(), "该小号当日允许提交补单记录已达上限"));
        }

        SubUserTask subUserTask = BeanUtil.convertTaskVO2PO(taskVO, log);
        subUserTaskMapper.insert(subUserTask);

        TaskReplaceOrder tro = new TaskReplaceOrder();
        tro.setTaskId(subUserTask.getId());
        tro.setOrderId(taskVO.getOrderId());
        tro.setOrderAmount(taskVO.getOrderAmount());
        tro.setOrderCommission(taskVO.getOrderCommission());
        tro.setPaymentStatus(PaymentStatusEnum.Unpaid);
        baseMapper.insert(tro);
        return new JSONResult<>(null, HttpStatus.OK.status, String.format(HttpStatus.OK.getMessage(), "提交补单记录"));
    }
}
