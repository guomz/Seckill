package com.guomz.Seckill.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class MD5Util {

	/**
	 * 将前端加密过的密码再次使用salt进行二次加密，返回加密后的字符串
	 * @param formPass
	 * @param salt
	 * @return
	 */
	public static String formPassToDBPass(String formPass,String salt)
	{
		String complexPass=""+salt.charAt(0)+salt.charAt(1)+salt.charAt(2)+formPass+salt.charAt(3)+salt.charAt(4)+salt.charAt(5);
		return DigestUtils.md5Hex(complexPass);
	}
	
	/**
	 * 模拟前端进行第一次md5加密
	 * @param inputPass
	 * @return
	 */
	private static String inputPassToFormPass(String inputPass)
	{
		String salt="a1b2c3d4";
		String complexPass=""+salt.charAt(0)+salt.charAt(1)+salt.charAt(2)+inputPass+salt.charAt(3)+salt.charAt(4)+salt.charAt(5);
		return DigestUtils.md5Hex(complexPass);
	}
	
	public static void main(String[] args) {
		String inputPass="123";
		String randomSalt = RandomStringUtils.random(6, "0123456789abcdefghijklmnopqrstuvwxyz");
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, randomSalt);
		System.out.println("formPass: "+formPass);//905eafad96a33be4d6c1999c92a0128e
		System.out.println("dbPass: "+dbPass);
		System.out.println("randomSalt: "+randomSalt);
	}
}
