package com.guomz.Seckill.interceptors;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomz.Seckill.anotations.AccessLimit;
import com.guomz.Seckill.dto.Result;
import com.guomz.Seckill.enums.CodeMsg;
import com.guomz.Seckill.util.RedisUtil;
@Component
public class AccessLimitInterceptor implements HandlerInterceptor{

	@Autowired
	private RedisUtil redisUtil;
	private ObjectMapper om;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		if(handler instanceof HandlerMethod)
		{
			//获取方法上的注解信息
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			AccessLimit accessLimit = handlerMethod.getMethod().getAnnotation(AccessLimit.class);
			if(accessLimit != null)
			{
				long limitTime = accessLimit.limitTime();
				int maxCount = accessLimit.maxCount();
				String requestURI = request.getRequestURI();
				Integer accessTime = redisUtil.getValue(requestURI, Integer.class);
				if(accessTime == null)
				{
					//第一次访问
					redisUtil.setValue(requestURI, 1, limitTime);
				}
				else if(accessTime < maxCount)
				{
					redisUtil.increValue(requestURI);
				}
				else if(accessTime >= maxCount)
				{
					//超过访问次数
					response.setContentType("application/json;charset=UTF-8");
					OutputStream out = response.getOutputStream();
					String str  = om.writeValueAsString(Result.success(CodeMsg.LIMIT_ACCESS, "反问频繁"));
					out.write(str.getBytes("UTF-8"));
					out.flush();
					out.close();
					return false;
				}
			}
		}
		return true;
	}

}
