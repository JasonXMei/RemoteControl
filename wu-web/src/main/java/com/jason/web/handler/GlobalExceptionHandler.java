package com.jason.web.handler;

import com.jason.common.enums.HttpStatus;
import com.jason.common.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(HttpServletRequest request,
			Exception exception) {
		log.error("request url [{}]", request.getRequestURL());
		LoggerUtil.printErrorLog(log, exception);

		return "redirect:/errorPage?status=" + HttpStatus.GLOBALEXCEPTION_ERROR.getStatus();
	}
}
