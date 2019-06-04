package com.jason.common.util;

import com.jason.common.po.*;
import com.jason.common.vo.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

public class BeanUtil {

    public static UserVO convertUserPO2VO(Mapper dozerMapper, User userPO){
        UserVO userVO = dozerMapper.map(userPO, UserVO.class);
        userVO.setPermissionInt(userPO.getPermission().getType());
        userVO.setSexStr(userPO.getSex().getDescription());
        userVO.setStatusStr(userPO.getStatus().getDescription());

        userVO.setValidTimeStr(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(userPO.getValidTime()));
        return userVO;
    }

    public static TaskReplaceOrderVO convertReplaceOrderPO2VO(Mapper dozerMapper, TaskReplaceOrder tro){
        TaskReplaceOrderVO trov = dozerMapper.map(tro, TaskReplaceOrderVO.class);
        trov.setPaymentStatusStr(tro.getPaymentStatus().getDescription());
        trov.setCreateTimeStr(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(tro.getCreateTime()));
        return trov;
    }

    public static <T> TaskPage<T> updateTaskPagesParams(TaskPage<T> pages, Logger log){
        try {
            if(!StringUtils.isEmpty(pages.getSearchStartDateStr())){
                pages.setSearchStartDate(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(pages.getSearchStartDateStr()).getTime());
            }
            if(!StringUtils.isEmpty(pages.getSearchEndDateStr())){
                pages.setSearchEndDate(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(pages.getSearchEndDateStr()).getTime());
            }
        } catch (ParseException e) {
            LoggerUtil.printErrorLog(log, e);
        }
        pages.setSize(10);
        pages.setCurrent(pages.getSearchCurrent());

        return  pages;
    }

    public static <T> void convertTaskPages(TaskPage<T> pages, long total, long size, long current,
                                                   String searchName, Integer searchStatus, String searchStartDateStr, String searchEndDateStr){
        pages.setTotal(total);
        pages.setPages(PageUtil.getPages(total, size));
        pages.setCurrent(current);
        pages.setSize(size);
        pages.setSearchName(searchName);
        pages.setSearchStatus(searchStatus);
        pages.setSearchStartDateStr(searchStartDateStr);
        pages.setSearchEndDateStr(searchEndDateStr);
    }

    public static TaskTagVO convertTagPO2VO(Mapper dozerMapper, TaskTag tt){
        TaskTagVO ttv = dozerMapper.map(tt, TaskTagVO.class);
        ttv.setTagTypeStr(tt.getTagType().getDescription());
        ttv.setCreateTimeStr(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(tt.getCreateTime()));
        return ttv;
    }

    public static SubUserVO convertSubUserPO2VO(Mapper dozerMapper, SubUserExt su) {
        SubUserVO userVO = dozerMapper.map(su, SubUserVO.class);
        userVO.setConnectStatusStr(su.getConnectStatus().getDescription());
        userVO.setOrderTimes(su.getOrderTimes() + "-" + su.getAllowOrderTimes());
        userVO.setTerminalStr(su.getTerminal().getDescription());
        userVO.setUserTypeStr(su.getUserType().getDescription());
        return userVO;
    }

    public static long getCurrentTimeStamp(Logger log){
        String currentTime = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(new Date());
        long currentDateTimeStamp = 0;
        try {
            currentDateTimeStamp = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(currentTime).getTime();
        } catch (ParseException e) {
            LoggerUtil.printErrorLog(log, e);
        }
        return currentDateTimeStamp;
    }

    public static SubUserTask convertTaskVO2PO(TaskVO taskVO, Logger log){
        SubUserTask subUserTask = new SubUserTask();
        long currentDateTimeStamp = getCurrentTimeStamp(log);
        subUserTask.setCreateTime(currentDateTimeStamp);
        subUserTask.setDescription(taskVO.getDescription());
        subUserTask.setShopId(taskVO.getShopId());
        subUserTask.setSubUserId(taskVO.getUserId());
        return  subUserTask;
    }
}
