package com.guomz.Seckill.dto;

import javax.validation.constraints.NotNull;

import com.guomz.Seckill.anotations.IsMobile;

/**
 * 接收登陆信息的dto
 * @author 12587
 *
 */
public class LoginDto {

	//手机号
	@NotNull
	@IsMobile
	private String mobile;
	@NotNull
	private String password;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginDto [mobile=" + mobile + ", password=" + password + "]";
	}
	
}
