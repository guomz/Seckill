package com.guomz.Seckill.dto;

import java.util.Date;

import com.guomz.Seckill.entity.Product;

public class ProductDto extends Product{

	private Double seckillPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
	public Double getSeckillPrice() {
		return seckillPrice;
	}
	public void setSeckillPrice(Double seckillPrice) {
		this.seckillPrice = seckillPrice;
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
		return "ProductDto [seckillPrice=" + seckillPrice + ", stockCount=" + stockCount + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
}
