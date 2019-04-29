package com.jason.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jason.common.po.TaskTag;
import com.jason.common.vo.TaskPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface TaskTagMapper extends BaseMapper<TaskTag> {

    TaskPage<TaskTag> findTagList(@Param("pg")TaskPage<TaskTag> pages);

}
