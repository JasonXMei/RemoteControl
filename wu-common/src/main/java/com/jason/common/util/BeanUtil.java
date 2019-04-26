package com.jason.common.util;

import com.jason.common.po.User;
import com.jason.common.vo.UserVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dozer.Mapper;

import java.util.Calendar;

public class BeanUtil {

    public static UserVO convertUserPO2VO(Mapper dozerMapper, User userPO){
        UserVO userVO = dozerMapper.map(userPO, UserVO.class);
        userVO.setPermissionInt(userPO.getPermission().getType());
        userVO.setSexStr(userPO.getSex().getDescription());
        userVO.setStatusStr(userPO.getStatus().getDescription());

        Calendar validCal = Calendar.getInstance();
        validCal.setTimeInMillis(userPO.getValidTime());
        userVO.setValidTimeStr(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(validCal));
        return userVO;
    }

}
