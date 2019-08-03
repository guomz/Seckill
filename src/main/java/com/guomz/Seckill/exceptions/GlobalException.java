package com.guomz.Seckill.exceptions;

import com.guomz.Seckill.enums.CodeMsg;
/**
 * 自定义异常
 * @author 12587
 *
 */
public class GlobalException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CodeMsg codeMsg;

	public GlobalException(CodeMsg codeMsg) {
		super(codeMsg.getMsg());
		// TODO Auto-generated constructor stub
		this.codeMsg=codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}

	public void setCodeMsg(CodeMsg codeMsg) {
		this.codeMsg = codeMsg;
	}

	
}
