package com.guomz.Seckill.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.guomz.Seckill.dto.ProductDto;

@Repository
public interface ProductDao {

	public List<ProductDto> queryAllProductDto();
	
	public ProductDto queryProductDtoById(Long id);
	
	public int reduceSeckillStockCount(Long id);
	
	public int reduceStockCount(Long id);
}
