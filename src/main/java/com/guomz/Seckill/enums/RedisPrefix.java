package com.guomz.Seckill.enums;

public enum RedisPrefix {

	USER_PREFIX("tk_", 3600L), GOODS_LIST_PREFIX("goods_list", 60L), GOODS_DETAIL_PREFIX("good_detail", 60L),
	ORDER_PREFIX("order_", null), PRODUCT_COUNT_PREFIX("product_", null), SECKILL_STATUS("seckill_", null),
	IN_LINE("inline_", null);
	
	private String prefix;
	private Long timeOut;
	
	private RedisPrefix(String prefix, Long timeOut)
	{
		this.prefix = prefix;
		this.timeOut = timeOut;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	
	
}
