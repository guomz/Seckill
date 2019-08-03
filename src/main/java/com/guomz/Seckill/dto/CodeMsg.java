package com.guomz.Seckill.dto;

import org.springframework.context.annotation.Description;

@SuppressWarnings("all")
/**
 * 存放正确与各种异常的报错信息
 * @author 12587
 *
 */
@Deprecated
public class CodeMsg {

	private String msg;
	private Integer code;
	
	private CodeMsg(String msg,Integer code)
	{
		this.msg=msg;
		this.code=code;
	}
	
	public static CodeMsg SUCCESS=new CodeMsg("success",0);
	public static CodeMsg SERVER_ERROR=new CodeMsg("服务端异常",500100);

	
	public static CodeMsg SESSION_ERROR=new CodeMsg("session失效",500210);
	public static CodeMsg PASSWORD_EMPTY=new CodeMsg("密码为空",500211);
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "CodeMsg [msg=" + msg + ", code=" + code + "]";
	}
}
