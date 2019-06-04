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

import java.util.ArrayList;
import java.util.Date;
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
        for (SubUserExt su: list) {
            voList.add(BeanUtil.convertSubUserPO2VO(dozerMapper, su));
        }
        pagesVO.setRecords(voList);

        BeanUtil.convertTaskPages(pagesVO, page.getTotal(), page.getSize(), page.getCurrent(), page.getSearchName(),
                page.getSearchStatus(), page.getSearchStartDateStr(),page.getSearchEndDateStr());

        return new JSONResult<>(pagesVO, HttpStatus.OK.getStatus(), String.format(HttpStatus.OK.getMessage(), "获取小号列表"));
    }

    @Override
    public JSONResult<UserDetailsVO> userDetail(Integer loginUserId, Integer taskUserId) {
        List<SubUser> taskUserList = baseMapper.selectList(new QueryWrapper<SubUser>().in("user_id", taskUserId));
        List<UserShop> shopList = userShopMapper.selectList(new QueryWrapper<UserShop>().in("user_id", loginUserId));
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setSubUserList(taskUserList);
        userDetailsVO.setShopList(shopList);
        return new JSONResult<>(userDetailsVO, HttpStatus.OK.getStatus(), String.format(HttpStatus.OK.getMessage(), "查询小号列表，店铺列表"));
    }
}
