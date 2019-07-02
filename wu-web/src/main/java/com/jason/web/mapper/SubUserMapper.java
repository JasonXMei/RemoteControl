package com.jason.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jason.common.po.SubUser;
import com.jason.common.po.SubUserExt;
import com.jason.common.vo.TaskPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface SubUserMapper extends BaseMapper<SubUser> {

    List<SubUserExt> findSubUserList(@Param("pg") TaskPage<SubUser> page);

    SubUserExt findSubUser(@Param("subUserId") Integer subUserId, @Param("currentDateTimeStamp") long currentDateTimeStamp);

    List<SubUserExt> getUserOrderTimes(@Param("userId") Integer userId, @Param("currentDateTimeStamp") long currentTimeStamp);

    SubUserExt findUserDesc(@Param("userId") Integer userId);
}
