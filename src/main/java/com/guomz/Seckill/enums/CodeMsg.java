package com.guomz.Seckill.enums;

public enum CodeMsg {

	SUCCESS(0,"成功"),SERVER_ERROR(500100,"服务端异常"),SESSION_ERROR(500210,"session失效"),EMPTY_PASSWORD(500211,"密码为空"),PASSWORD_ERROR(500212,"密码错误"),EMPTY_MOBILE_NUMBER(500213,"手机号不能为空"),MOBILE_ERROR(500214,"手机号错误")
	,BIND_ERROR(500215,"绑定异常"),NO_STOCK(500510,"没有库存"),REPEAT_KILL(500520,"重复秒杀"),REPEAT_CLICK(500530,"排队中"),LIMIT_ACCESS(500101,"频繁访问");
	
	private int code;
	private String msg;
	
	private CodeMsg(int code,String msg)
	{
		this.code=code;
		this.msg=msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
