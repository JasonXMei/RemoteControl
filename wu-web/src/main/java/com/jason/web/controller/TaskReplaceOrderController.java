package com.jason.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Controller
@RequestMapping("/task")
public class TaskReplaceOrderController {

	@RequestMapping("/list")
	public ModelAndView taskList(){
		ModelAndView mav = new ModelAndView("task/taskList");
		return mav;
	}
	
	@RequestMapping("/info")
	public ModelAndView taskInfo(){
		ModelAndView mav = new ModelAndView("task/taskInfo");
		return mav;
	}
	
}
