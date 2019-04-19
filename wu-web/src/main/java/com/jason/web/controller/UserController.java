package com.jason.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView login(User user, RedirectAttributes attributes){
		User loginUser = userService.getOne(new QueryWrapper<User>()
				.eq("user_name", user.getUserName())
				.eq("password", user.getPassword())
				.last("LIMIT 1"));
		
		ModelAndView mav = new ModelAndView();
		if(loginUser != null){
			mav.setViewName("redirect:list");
			String jwt = JWTUtil.createToken(loginUser.getPassword(), loginUser.getId());
			mav.addObject("jwt", jwt);
			mav.addObject("user", loginUser);
			return mav;
		}
		mav.setViewName("redirect:/errorPage");
		attributes.addFlashAttribute("message", "登录用户不存在！");
		return mav;
	}
	
	@RequestMapping("/list")
	public ModelAndView userList(User user){
		ModelAndView mav = new ModelAndView("user/userList");
		return mav;
	}
	
	@RequestMapping("/info")
	public ModelAndView userInfo(User user){
		ModelAndView mav = new ModelAndView("user/userInfo");
		return mav;
	}
}
