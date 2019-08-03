package com.guomz.Seckill.dto;

import com.guomz.Seckill.entity.User;
/**
 * 消息队列的消息
 * @author 12587
 *
 */
public class Message {

	private ProductDto productDto;
	private User user;
	public ProductDto getProductDto() {
		return productDto;
	}
	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Message [productDto=" + productDto + ", user=" + user + "]";
	}
	
}
