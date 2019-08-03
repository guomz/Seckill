package com.guomz.Seckill.daoTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.guomz.Seckill.SeckillApplicationTests;
import com.guomz.Seckill.dao.ProductDao;

public class ProductDaoTest extends SeckillApplicationTests{

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testQueryAll()
	{
		System.out.println(productDao.queryAllProductDto());
	}
	
	@Test
	public void testQueryById()
	{
		System.out.println(productDao.queryProductDtoById(1L));
	}
}
