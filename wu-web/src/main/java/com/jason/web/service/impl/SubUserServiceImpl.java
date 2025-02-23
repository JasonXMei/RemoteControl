package com.jason.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.enums.HttpStatus;
import com.jason.common.po.SubUser;
import com.jason.common.po.SubUserExt;
import com.jason.common.po.UserShop;
import com.jason.common.util.BeanUtil;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.SubUserVO;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.UserDetailsVO;
import com.jason.web.mapper.SubUserMapper;
import com.jason.web.mapper.UserShopMapper;
import com.jason.web.service.SubUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
public class SubUserServiceImpl extends ServiceImpl<SubUserMapper, SubUser> implements SubUserService {

    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private UserShopMapper userShopMapper;

    @Override
    public JSONResult<TaskPage<SubUserVO>> handList(TaskPage<SubUser> page) {
        page.setSearchStartDateStr(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(new Date()));
        page.setSearchEndDateStr(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(new Date()));
        page = BeanUtil.updateTaskPagesParams(page, log);
        List<SubUserExt> list = baseMapper.findSubUserList(page);

        TaskPage<SubUserVO> pagesVO = new TaskPage<>();
        List<SubUserVO> voList = new ArrayList<>();
        Map<Integer, String> userDescMap = new HashMap<>();
        for (SubUserExt su: list) {
            if(!userDescMap.containsKey(su.getUserId())){
                SubUserExt desc = baseMapper.findUserDesc(su.getUserId());
                if(desc != null && desc.getCreateTime() != null){
                    String latestOrderTime = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(desc.getCreateTime());
                    userDescMap.put(su.getUserId(), latestOrderTime + "/\n" + desc.getShopName() + "/" + desc.getSubUserName());
                }else{
                    userDescMap.put(su.getUserId(), " ");
                }
            }
            voList.add(BeanUtil.convertSubUserPO2VO(dozerMapper, su, userDescMap));
        }
        pagesVO.setRecords(voList);

        BeanUtil.convertTaskPages(pagesVO, page.getTotal(), page.getSize(), page.getCurrent(), page.getSearchName(),
                page.getSearchStatus(), page.getSearchStartDateStr(),page.getSearchEndDateStr());

        return new JSONResult<>(pagesVO, HttpStatus.OK.getStatus(), String.format(HttpStatus.OK.getMessage(), "获取小号列表"));
    }

    @Override
    public String getUserOrderTimes(Integer userId) {
        List<SubUserExt> list = baseMapper.getUserOrderTimes(userId, BeanUtil.getCurrentTimeStamp(log));
        StringBuilder sb = new StringBuilder();
        for(SubUserExt subUserExt : list){
            Integer orderTimes = subUserExt.getOrderTimes();
            Integer allowOrderTimes = subUserExt.getAllowOrderTimes();
            if(orderTimes >= allowOrderTimes){
                sb.append("小号名:" + subUserExt.getSubUserName() + ",日" + orderTimes + "笔,当日已满。\n");
            }else{
                sb.append("小号名:" + subUserExt.getSubUserName() + ",日" + orderTimes + "笔,日限制" + allowOrderTimes + "笔,当日尚可补单。\n");
            }
        }
        return sb.toString();
    }

    @Override
    public JSONResult<UserDetailsVO> userDetail(Integer loginUserId, Integer taskUserId) {
        List<SubUser> taskUserList = baseMapper.selectList(new QueryWrapper<SubUser>().in("user_id", taskUserId));
        List<SubUserVO> subUserVOList = new ArrayList<>();
        for(SubUser subUser : taskUserList){
            subUserVOList.add(BeanUtil.convertSubUserPO2VO(dozerMapper, subUser));
        }
        List<UserShop> shopList = userShopMapper.selectList(new QueryWrapper<UserShop>().in("user_id", loginUserId));
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setSubUserList(subUserVOList);
        userDetailsVO.setShopList(shopList);
        return new JSONResult<>(userDetailsVO, HttpStatus.OK.getStatus(), String.format(HttpStatus.OK.getMessage(), "查询小号列表，店铺列表"));
    }

    @Override
    public Integer handleStatus(Integer userId) {
        SubUser userPO = baseMapper.selectById(userId);
        if(userPO != null){
            return userPO.getConnectStatus().getStatus();
        }
        return -1;
    }

}
