package com.jason.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jason.common.po.User;
import com.jason.common.util.JWTUtil;
import com.jason.web.service.UserService;

@Component
@Slf4j
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
		
		String requestURL = request.getRequestURL().toString();
		String redirectLocation = request.getContextPath() + "/errorPage";
		log.info("interceptor url[{}],redirect to [{}]", requestURL, redirectLocation);
		
		request.setAttribute("message", "token验证失败");
		request.getRequestDispatcher(redirectLocation).forward(request, response);
		//response.sendRedirect(request.getContextPath() + "/errorPage");
		return false;
	}
}
