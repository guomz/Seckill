package com.guomz.Seckill.daoTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.guomz.Seckill.SeckillApplicationTests;
import com.guomz.Seckill.dao.OrderDao;

public class OrderDaoTest extends SeckillApplicationTests{

	@Autowired
	private OrderDao orderDao;
	
	@Test
	public void testQuery()
	{
		System.out.println(orderDao.querySeckillOrderByUserAndProduct(1L, 1L));
	}
}
