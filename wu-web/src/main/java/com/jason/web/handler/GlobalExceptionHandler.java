package com.jason.web.handler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.jason.common.util.LoggerUtil;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request,
			Exception exception) {
		ModelAndView mav = new ModelAndView("errorPage");
		log.error("request url [{}]", request.getRequestURL());
		LoggerUtil.printErrorLog(log, exception);
		mav.addObject("message", "服务器太忙了,请稍后重试!");
		return mav;
	}
}
