package com.jason.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping("/errorPage")
	public ModelAndView errorPage(@ModelAttribute("message") String message){
		ModelAndView mav = new ModelAndView("errorPage");
		mav.addObject("message", message);
		return mav;
	}
}
