package com.guomz.Seckill.anotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.guomz.Seckill.validators.IsMobileValidator;
/**
 * 判断手机号的jsr注解
 * @author 12587
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsMobileValidator.class })
public @interface IsMobile {

	//自定义变量，用于标识该注解默认为开启
	boolean required() default true;
	
	String message() default "手机号码不正确";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
