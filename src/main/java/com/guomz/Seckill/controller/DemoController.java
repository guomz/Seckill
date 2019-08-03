package com.guomz.Seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guomz.Seckill.enums.CodeMsg;
import com.guomz.Seckill.dto.Result;
@Controller
@SuppressWarnings("all")
public class DemoController {

	@RequestMapping("/hello")
	public String helloName(Model model)
	{
		model.addAttribute("name", "guomz");
		return "index";
	}
	
	@RequestMapping("/helloresult")
	@ResponseBody
	public Result<String> helloResult()
	{
		return Result.success(CodeMsg.SUCCESS, "hello aaa");
	}
	
	@RequestMapping("/helloerror")
	@ResponseBody
	public Result helloError()
	{
		return Result.error(CodeMsg.SERVER_ERROR);
	}
}
