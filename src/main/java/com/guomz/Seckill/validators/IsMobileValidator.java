package com.guomz.Seckill.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.guomz.Seckill.anotations.IsMobile;
/**
 * 定义手机号验证注解的校验器
 * @author 12587
 *
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{

	boolean required;
	
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		//获取注解的属性
		this.required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(required)
		{
			//对手机号码简单验证，只包含数字
			boolean result = StringUtils.containsOnly(value, "0123456789");
			//System.out.println("mobile validation result:"+result);
			return result;
		}
		else
		{
			return true;
		}
	}

}
