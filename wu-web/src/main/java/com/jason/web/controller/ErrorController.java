package com.jason.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	@RequestMapping("/errorPage")
	public String errorPage(HttpServletRequest request){
		return "errorPage";
	}
}
