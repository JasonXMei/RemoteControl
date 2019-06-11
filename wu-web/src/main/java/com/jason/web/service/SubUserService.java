package com.jason.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jason.common.po.SubUser;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.SubUserVO;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.UserDetailsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface SubUserService extends IService<SubUser> {

    JSONResult<TaskPage<SubUserVO>> handList(TaskPage<SubUser> page);

    JSONResult<UserDetailsVO> userDetail(Integer loginUserId, Integer taskUserId);

    Integer handleStatus(Integer userId);
}
