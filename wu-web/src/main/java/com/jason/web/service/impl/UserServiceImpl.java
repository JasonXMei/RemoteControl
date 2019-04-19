package com.jason.web.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.po.User;
import com.jason.web.mapper.UserMapper;
import com.jason.web.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
