package com.jason.web.controller;

import com.jason.common.enums.HttpStatus;
import com.jason.common.util.JWTUtil;
import com.jason.web.service.UserService;
import com.jason.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class ErrorController {

	@Autowired
	private UserService userService;

	@GetMapping("/errorPage")
	public String errorPage(HttpServletRequest request, @RequestParam(value = "status", required = false) Integer status){
		String jwt = JWTUtil.checkAndHandleSessionToken(HttpUtil.getSessionAttribute (request,false,"loginUserToken", String.class));
		userService.verifyJWT(jwt, request, true);
		if(status != null){
		    String errorMessage = HttpStatus.getHttpStatusByStatus(status).getMessage();
            request.setAttribute("message", errorMessage);
        }
		return "errorPage";
	}
}
