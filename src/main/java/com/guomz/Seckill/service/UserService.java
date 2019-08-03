package com.guomz.Seckill.service;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guomz.Seckill.dao.UserDao;
import com.guomz.Seckill.dto.LoginDto;
import com.guomz.Seckill.entity.User;
import com.guomz.Seckill.enums.CodeMsg;
import com.guomz.Seckill.enums.RedisPrefix;
import com.guomz.Seckill.exceptions.GlobalException;
import com.guomz.Seckill.util.MD5Util;
import com.guomz.Seckill.util.RedisUtil;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisUtil redisUtil;
	
	public final String COOKIE_USER_TOKEN = "userToken";
	
	//public final String REDIS_USER_KEY = "tk_";
	
	/**
	 * 登陆处理
	 * @param logindto
	 * @return
	 * @throws JsonProcessingException 
	 */
	public boolean login(LoginDto logindto,HttpServletResponse resp) throws JsonProcessingException
	{
		if(logindto==null)
		{
			 throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		
		String mobile = logindto.getMobile();
		String password = logindto.getPassword();
		
		User user = userDao.queryUserByMobile(mobile);
		if(user!=null)
		{
			String salt = user.getSalt();
			String dbPass = user.getPassword();
			if(MD5Util.formPassToDBPass(password, salt).equals(dbPass))
			{
				//登陆成功后生成token写入缓存，并将token带给cookie
				addCookie(resp, user);
				return true;
			}
			else
			{
				throw new GlobalException(CodeMsg.PASSWORD_ERROR);
			}
		}
		else
		{
			throw new GlobalException(CodeMsg.MOBILE_ERROR);
		}
	}
	
	/**
	 * 将生成token并存储
	 * @param resp
	 * @param user
	 * @throws JsonProcessingException
	 */
	public void addCookie(HttpServletResponse resp, User user) throws JsonProcessingException
	{
		//cookie的用户token通过uuid生成，前缀为常量
		String token = UUID.randomUUID().toString().replace("-", "");
		redisUtil.setValue(RedisPrefix.USER_PREFIX.getPrefix() + token, user, RedisPrefix.USER_PREFIX.getTimeOut());
		Cookie cookie = new Cookie(this.COOKIE_USER_TOKEN, token);
		cookie.setMaxAge(60*60);
		resp.addCookie(cookie);
	}
}
