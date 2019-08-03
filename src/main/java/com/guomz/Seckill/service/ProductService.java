package com.guomz.Seckill.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guomz.Seckill.dao.ProductDao;
import com.guomz.Seckill.dto.ProductDto;
import com.guomz.Seckill.entity.Order;
import com.guomz.Seckill.enums.RedisPrefix;
import com.guomz.Seckill.util.RedisUtil;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RedisUtil redisUtil;
	
	public List<ProductDto> listAllProduct()
	{
		return productDao.queryAllProductDto();
	}
	
	public ProductDto getProductDtoById(Long id)
	{
		return productDao.queryProductDtoById(id);
	}
	
	/**
	 * 处理秒杀业务，包含减库存、生成订单
	 * @param userId
	 * @param productId
	 * @return
	 * @throws JsonProcessingException 
	 */
	@Transactional
	public Order handleSeckill(Long userId,Long productId) throws JsonProcessingException
	{
		//减库存
		boolean seckillResult = this.reduceProductCount(productId);
		if(seckillResult == false)
		{
			//秒杀失败，在缓存中写入状态，供轮询时查询
			redisUtil.setValue(RedisPrefix.SECKILL_STATUS.getPrefix() + userId + "_" + productId, false);
			return null;
		}
		//生成订单
		ProductDto productDto = getProductDtoById(productId);
		Order order = new Order();
		order.setUserId(userId);
		order.setProductId(productId);
		order.setCreateDate(new Date());
		order.setDeliveryAddrId(1L);
		order.setOrderChannel(1);
		order.setProductCount(1);
		order.setProductName(productDto.getProductName());
		order.setProductPrice(productDto.getSeckillPrice());
		order.setStatus(0);
		orderService.generateOrder(order);
		return order;
	}
	
	/**
	 * 减少商品总库存与秒杀库存
	 * @param id
	 */
	public boolean reduceProductCount(Long id)
	{
		int seckillStockCount = productDao.reduceSeckillStockCount(id);
		int stockCount = productDao.reduceStockCount(id);
		return (seckillStockCount>0&&stockCount>0);
	}
}
