package com.guomz.Seckill.RedisUtilTest;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guomz.Seckill.SeckillApplicationTests;
import com.guomz.Seckill.entity.User;
import com.guomz.Seckill.util.RedisUtil;

public class RedisUtilTest extends SeckillApplicationTests{

	@Autowired
	private RedisUtil redisUtil;
	
	@Test
	public void testSet()
	{
		User user = new User();
		user.setNickname("guomz");
		user.setPassword("123");
		try {
			redisUtil.setValue("user", user);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGet()
	{
		try {
			User user = redisUtil.getValue("user", User.class);
			System.out.println(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
