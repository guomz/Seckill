package com.guomz.Seckill.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.guomz.Seckill.interceptors.AccessLimitInterceptor;
import com.guomz.Seckill.interceptors.UserTokenInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{

	@Autowired
	private UserTokenInterceptor userTokenInterceptor;
	@Autowired
	private AccessLimitInterceptor accessLimitInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		//WebMvcConfigurer.super.addInterceptors(registry);
		
		InterceptorRegistration userInterceptor = registry.addInterceptor(userTokenInterceptor);
		userInterceptor.addPathPatterns("/goods/**");
		InterceptorRegistration accessInterceptor = registry.addInterceptor(accessLimitInterceptor);
		accessInterceptor.addPathPatterns("/goods/doseckill");
	}

	
}
