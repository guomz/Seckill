package com.guomz.Seckill.interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.guomz.Seckill.entity.User;
import com.guomz.Seckill.enums.RedisPrefix;
import com.guomz.Seckill.service.UserService;
import com.guomz.Seckill.util.RedisUtil;
@Component
public class UserTokenInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
		//cookie不存在则跳转到登录页
		if(cookies==null)
		{
			response.sendRedirect("/Seckill/login");
			return false;
		}
		for(Cookie cookie:cookies)
		{
			if(cookie.getName().equals(userService.COOKIE_USER_TOKEN))
			{
				//取出缓存
				User user = redisUtil.getValue(RedisPrefix.USER_PREFIX.getPrefix() + cookie.getValue(), User.class);
				if(user != null)
				{
					//刷新缓存时间
					redisUtil.setValue(RedisPrefix.USER_PREFIX.getPrefix(), user, RedisPrefix.USER_PREFIX.getTimeOut());
					request.setAttribute("user", user);
					return true;
				}
				else
				{
					response.sendRedirect("/Seckill/login");
					return false;
				}
			}
		}
		response.sendRedirect("/Seckill/login");
		return false;
	}

}
