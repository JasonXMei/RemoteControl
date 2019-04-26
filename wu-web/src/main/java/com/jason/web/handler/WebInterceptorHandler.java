package com.jason.web.handler;

import com.jason.common.enums.HttpStatus;
import com.jason.common.util.JWTUtil;
import com.jason.web.config.ParamsConfig;
import com.jason.web.service.UserService;
import com.jason.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class WebInterceptorHandler extends HandlerInterceptorAdapter{

	@Autowired
	private UserService userService;
	@Autowired
    private ParamsConfig paramsConfig;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURL = request.getRequestURL().toString();
		String jwt = null;
		if (requestURL.contains("token")){
			jwt = JWTUtil.checkAndHandleURLToken(requestURL);
		}else{
            jwt = JWTUtil.checkAndHandleSessionToken(HttpUtil.getSessionAttribute (request,false, "loginUserToken", String.class));
		}

		if(userService.verifyJWT(jwt, request)){
			return true;
		}

        String redirectLocation = request.getContextPath() + "/errorPage?status=" + HttpStatus.INTERCEPTOR_NOT_ALLOW.getStatus();
        log.info("interceptor url[{}],redirect to [{}]", requestURL, redirectLocation);

		response.sendRedirect(redirectLocation);
		return false;
	}
}
