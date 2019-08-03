package com.guomz.Seckill.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
@SuppressWarnings("all")
public class RedisUtil {

	@Autowired
	private StringRedisTemplate template;
	@Autowired
	private ObjectMapper om;
	
	/**
	 * 永久存放值
	 * @param key
	 * @param object
	 * @throws JsonProcessingException
	 */
	public void setValue(String key,Object object) throws JsonProcessingException
	{
		String jsonStr = om.writeValueAsString(object);
		template.opsForValue().set(key, jsonStr);
	}
	
	/**
	 * 制定超时时间存放
	 * @param key
	 * @param object
	 * @param timeOut
	 * @throws JsonProcessingException
	 */
	public void setValue(String key, Object object, Long timeOut) throws JsonProcessingException
	{
		String jsonStr = om.writeValueAsString(object);
		template.opsForValue().set(key, jsonStr, timeOut, TimeUnit.SECONDS);
	}
	
	public <T> T getValue(String key,Class<T> c) throws JsonParseException, JsonMappingException, IOException
	{
		String jsonStr = template.opsForValue().get(key);
		if(jsonStr == null)
		{
			return null;
		}
		T object = om.readValue(jsonStr, c);
		return object;
	}
	
	public Object getCollectionValue(String key, JavaType javaType) throws JsonParseException, JsonMappingException, IOException
	{
		String jsonStr = template.opsForValue().get(key);
		if(jsonStr == null)
		{
			return null;
		}
		return om.readValue(jsonStr, javaType);
	}
	/**
	 * 对整形value做减法，本业务用于预减库存
	 * @param key
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public Long decrValue(String key) throws JsonParseException, JsonMappingException, IOException
	{
		return template.opsForValue().decrement(key);
		//return this.getValue(key, Integer.class);
	}
	
	/**
	 * 对整形做加法，本业务中用于判断用户在规定时间内的访问次数
	 * @param key
	 * @return
	 */
	public Long increValue(String key)
	{
		return template.opsForValue().increment(key);
	}
	
}
