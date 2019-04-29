package com.jason.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jason.common.po.TaskTag;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskTagVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface TaskTagService extends IService<TaskTag> {

    TaskPage<TaskTagVO> handleList(TaskPage<TaskTag> pages);

}
