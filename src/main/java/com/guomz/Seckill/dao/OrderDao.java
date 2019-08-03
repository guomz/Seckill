package com.guomz.Seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.guomz.Seckill.entity.Order;
import com.guomz.Seckill.entity.SeckillOrder;

@Repository
public interface OrderDao {

	public SeckillOrder querySeckillOrderByUserAndProduct(@Param("userId")Long userId,
			@Param("productId")Long productId);
	
	public Order queryOrderById(Long orderId);
	
	public void insertOrder(Order order);
	
	public void insertSeckillOrder(SeckillOrder seckillOrder);
}
