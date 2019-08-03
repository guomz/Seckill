package com.guomz.Seckill.dto;
import com.guomz.Seckill.enums.CodeMsg;
@SuppressWarnings("all")
public class Result<T> {

	private String msg;
	private Integer code;
	private T data;
	//成功构造函数
	private Result(CodeMsg codeMsg,T data)
	{
		this.msg=codeMsg.getMsg();
		this.code=codeMsg.getCode();
		this.data=data;
	}
	//失败构造函数
	private Result(CodeMsg codeMsg)
	{
		this.msg=codeMsg.getMsg();
		this.code=codeMsg.getCode();
		this.data=null;
	}
	
	public static<T> Result<T> success(CodeMsg codeMsg,T data)
	{
		return new Result<T>(codeMsg,data);
	}
	public static<T> Result<T> error(CodeMsg codeMsg)
	{
		return new Result<T>(codeMsg);
	}
	
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Result [msg=" + msg + ", code=" + code + ", data=" + data + "]";
	}
	
	
}
