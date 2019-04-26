package com.jason.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jason.web.handler.WebInterceptorHandler;

@Configuration
@EnableWebMvc
public class WebCofig implements WebMvcConfigurer{
	
	@Autowired
	private ParamsConfig paramsConfig;
	@Autowired
	private WebInterceptorHandler webInterceptorHandler;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:/" + paramsConfig.imagePath);
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptorHandler)
        	.addPathPatterns("/**")
        	.excludePathPatterns("/user/login", "/user/info/**", "/user/saveOrUpdate/**", "/errorPage/**", "/static/**", "/images/**");
	}
}
