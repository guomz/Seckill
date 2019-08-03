package com.guomz.Seckill.serviceTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guomz.Seckill.SeckillApplicationTests;
import com.guomz.Seckill.service.ProductService;

public class ProductServiceTest extends SeckillApplicationTests{

	@Autowired
	private ProductService productService;
	
	@Test
	public void testSeckill() throws JsonProcessingException
	{
		productService.handleSeckill(1L, 1L);
	}
}
