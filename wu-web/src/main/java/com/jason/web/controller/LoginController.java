package com.jason.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping("/userList")
	public ModelAndView userList(){
		ModelAndView mav = new ModelAndView("userList");
		return mav;
	}
	
	@RequestMapping("/userInfo")
	public ModelAndView userInfo(){
		ModelAndView mav = new ModelAndView("userInfo");
		return mav;
	}
	
	@RequestMapping("/taskList")
	public ModelAndView taskList(){
		ModelAndView mav = new ModelAndView("taskList");
		return mav;
	}
	
	@RequestMapping("/taskInfo")
	public ModelAndView taskInfo(){
		ModelAndView mav = new ModelAndView("taskInfo");
		return mav;
	}
}
