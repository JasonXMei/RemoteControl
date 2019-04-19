package com.jason.web.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.po.TaskTag;
import com.jason.web.mapper.TaskTagMapper;
import com.jason.web.service.TaskTagService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Service
public class TaskTagServiceImpl extends ServiceImpl<TaskTagMapper, TaskTag> implements TaskTagService {

}
