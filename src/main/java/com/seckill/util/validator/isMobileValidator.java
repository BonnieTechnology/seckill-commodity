package com.seckill.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.seckill.common.util.ValidatorUtil;


public class isMobileValidator implements ConstraintValidator<isMobile, String> {

	private boolean required = false;
	
	//初始化方法，可以拿到定义的注解是否为必须的
	public void initialize(isMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}
	
	/**
	 * 判断注解是否合法
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}


}
