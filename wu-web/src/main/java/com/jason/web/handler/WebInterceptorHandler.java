package com.jason.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jason.common.po.User;
import com.jason.common.util.JWTUtil;
import com.jason.web.service.UserService;

public class WebInterceptorHandler extends HandlerInterceptorAdapter{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String jwt = JWTUtil.checkAndHandleToken(request.getHeader("Authorization"));
		
		if(!StringUtils.isEmpty(jwt)){
			int userId = JWTUtil.decodeToken(jwt);
			User user = userService.getById(userId);
			
			if(user != null){
				return JWTUtil.verifyToken(jwt, user.getPassword());
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/index/errorIndex");
		return false;
	}
}
