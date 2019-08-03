package com.guomz.Seckill.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.guomz.Seckill.dao.OrderDao;
import com.guomz.Seckill.entity.Order;
import com.guomz.Seckill.entity.SeckillOrder;
import com.guomz.Seckill.enums.RedisPrefix;
import com.guomz.Seckill.util.RedisUtil;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 获取秒杀订单
	 * @param userId
	 * @param productId
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public SeckillOrder getSeckillOrderByUserAndProduct(Long userId,Long productId) throws JsonParseException, JsonMappingException, IOException
	{
		return redisUtil.getValue(RedisPrefix.ORDER_PREFIX.getPrefix() + userId + "_" + productId, SeckillOrder.class);
		//return orderDao.queryOrderByUserAndProduct(userId, productId);
	}
	
	/**
	 * 获取普通订单
	 * @param orderId
	 * @return
	 */
	public Order getOrderById(Long orderId)
	{
		return orderDao.queryOrderById(orderId);
	}
	
	/**
	 * 生成订单与秒杀订单
	 * @param order
	 * @return
	 * @throws JsonProcessingException 
	 */
	public Order generateOrder(Order order) throws JsonProcessingException
	{
		orderDao.insertOrder(order);
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setOrderId(order.getId());
		seckillOrder.setUserId(order.getUserId());
		seckillOrder.setProductId(order.getProductId());
		orderDao.insertSeckillOrder(seckillOrder);
		//按照order_用户id_商品id的方式将秒杀订单插入缓存
		redisUtil.setValue(RedisPrefix.ORDER_PREFIX.getPrefix() + order.getUserId()  + "_" + order.getProductId(), seckillOrder);
		return order;
	}
}
