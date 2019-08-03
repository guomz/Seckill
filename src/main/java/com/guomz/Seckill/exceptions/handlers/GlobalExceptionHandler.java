package com.guomz.Seckill.exceptions.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guomz.Seckill.dto.Result;
import com.guomz.Seckill.enums.CodeMsg;
import com.guomz.Seckill.exceptions.GlobalException;
/**
 * 定义全局异常处理，注意异常需要被抛出
 * @author 12587
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest req,Exception e)
	{
		e.printStackTrace();
		if(e instanceof GlobalException)
		{
			return Result.error(((GlobalException) e).getCodeMsg());
		}
		//处理因jsr校验产生的绑定异常
		if(e instanceof BindException)
		{
			BindException ex = (BindException) e;
			//获取异常的第一个异常信息
			ObjectError objectError = ex.getAllErrors().get(0);
			Result<String> result = Result.error(CodeMsg.BIND_ERROR);
			result.setMsg(objectError.getDefaultMessage());
			return result;
		}
		return Result.error(CodeMsg.SERVER_ERROR);
	}
}
