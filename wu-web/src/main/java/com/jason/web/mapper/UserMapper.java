package com.jason.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jason.common.po.User;
import com.jason.common.vo.UserPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface UserMapper extends BaseMapper<User> {

    UserPage<User> findUserList(@Param("pg") UserPage<User> userPage);

    void updateId(@Param("exepectedUserId") Integer exepectedUserId, @Param("currentUserId") Integer currentUserId);

    int getMaxId();
}
