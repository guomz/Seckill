package com.guomz.Seckill.entity;

import java.util.Date;

public class SeckillProduct {
	private Long id;
	private Long productId;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "SeckillProduct [id=" + id + ", productId=" + productId + ", stockCount=" + stockCount + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	
}
