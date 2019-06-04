package com.jason.web.controller;


import com.jason.common.enums.HttpStatus;
import com.jason.common.po.TaskTag;
import com.jason.common.po.User;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.TaskTagVO;
import com.jason.web.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Controller
@RequestMapping("/tag")
public class TaskTagController {

    @Autowired
    private TaskTagService taskTagService;

    @RequestMapping("/list")
    public String taskList(TaskPage<TaskTag> pages, HttpServletRequest request){
        User loginUser = (User)request.getAttribute("user");
        pages.setSearchUserId(loginUser.getId());
        TaskPage<TaskTagVO> list = taskTagService.handleList(pages);
        request.setAttribute("tagList", list);
        return "task/tagList";
    }

    @RequestMapping("/list/client")
    @ResponseBody
    public JSONResult<TaskPage<TaskTagVO>> taskListClient(TaskPage<TaskTag> pages, HttpServletRequest request){
        TaskPage<TaskTagVO> list = taskTagService.handleList(pages);
        JSONResult<TaskPage<TaskTagVO>> result = new JSONResult<>(list, HttpStatus.OK.status, String.format(HttpStatus.OK.getMessage(), "获取标签列表"));
        return result;
    }

}
