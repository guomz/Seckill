package com.guomz.Seckill.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guomz.Seckill.dto.LoginDto;
import com.guomz.Seckill.dto.Result;
import com.guomz.Seckill.enums.CodeMsg;
import com.guomz.Seckill.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String toLogin()
	{
		return "/index";
	}
	
	@RequestMapping(value="/dologin",method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> doLogin(@Valid LoginDto logindto, HttpServletResponse resp) throws JsonProcessingException
	{
		System.out.println(logindto);
		boolean result = userService.login(logindto, resp);
		if(result)
		{
			return Result.success(CodeMsg.SUCCESS, true);
		}
		else
		{
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
