package com.guomz.Seckill.entity;

public class SeckillOrder {

	private Long id;
	private Long userId;
	private Long  orderId;
	private Long productId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "SeckillOrder [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", productId=" + productId
				+ "]";
	}
	
}
