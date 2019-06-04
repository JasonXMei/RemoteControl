package com.jason.web.controller;

import com.jason.common.po.SubUser;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.SubUserVO;
import com.jason.common.vo.TaskPage;
import com.jason.common.vo.UserDetailsVO;
import com.jason.web.service.SubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Controller
@RequestMapping("/client/subUser/")
public class SubUserController {

	@Autowired
	private SubUserService subUserService;

    @RequestMapping("list")
    @ResponseBody
    public JSONResult<TaskPage<SubUserVO>> subUserList(TaskPage<SubUser> page){
        return subUserService.handList(page);
    }

    @ResponseBody
    @RequestMapping("{loginUserId}/{taskUserId}/get")
    public  JSONResult<UserDetailsVO> userDetail(
        @PathVariable("loginUserId") Integer loginUserId,
        @PathVariable("taskUserId") Integer taskUserId
    ){
        return subUserService.userDetail(loginUserId, taskUserId);
    }
}
