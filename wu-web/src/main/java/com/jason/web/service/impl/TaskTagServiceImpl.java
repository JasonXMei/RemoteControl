package com.jason.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.po.TaskTag;
import com.jason.common.util.BeanUtil;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskTagVO;
import com.jason.web.mapper.TaskTagMapper;
import com.jason.web.service.TaskTagService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class TaskTagServiceImpl extends ServiceImpl<TaskTagMapper, TaskTag> implements TaskTagService {

    @Autowired
    private Mapper dozerMapper;

    @Override
    public TaskPage<TaskTagVO> handleList(TaskPage<TaskTag> pages) {
        pages = BeanUtil.updateTaskPagesParams(pages, log);
        pages = baseMapper.findTagList(pages);

        TaskPage<TaskTagVO> pagesVO = new TaskPage<>();
        List<TaskTagVO> list = new ArrayList<>();
        for (TaskTag tt : pages.getRecords()) {
            list.add(BeanUtil.convertTagPO2VO(dozerMapper, tt));
        }

        pagesVO.setRecords(list);
        BeanUtil.convertTaskPages(pagesVO, pages.getTotal(), pages.getSize(), pages.getCurrent(), pages.getSearchName(),
                pages.getSearchStatus(), pages.getSearchStartDateStr(),pages.getSearchEndDateStr());
        return pagesVO;
    }
}
