package com.jason.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.common.po.User;
import com.jason.common.util.JWTUtil;
import com.jason.web.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request, HttpServletResponse response){
		User loginUser = userService.getOne(new QueryWrapper<User>()
				.eq("user_name", user.getUserName())
				.eq("password", user.getPassword())
				.last("LIMIT 1"));
		
		if(loginUser != null){
			String jwt = JWTUtil.createToken(loginUser.getPassword(), loginUser.getId());
			request.setAttribute("jwt", jwt);
			request.setAttribute("user", loginUser);
			return "user/userList";
		}
		
		request.setAttribute("message", "登录用户不存在！");
		return "errorPage";
	}
	
	@RequestMapping("/list")
	public String userList(User user, HttpServletRequest request, HttpServletResponse response){
		return "user/userList";
	}
	
	@RequestMapping("/info")
	public ModelAndView userInfo(User user){
		ModelAndView mav = new ModelAndView("user/userInfo");
		return mav;
	}
}
