package com.seckill.util.validator;
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
/**
 * 自定义的校验注解手机号
 * @author Administrator
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {isMobileValidator.class})//用来告诉系统自定义的注解
public @interface isMobile {
	
	//true默认必须要有
	boolean required() default true;
	//校验不通过显示的信息
	String message() default "手机号码格式不正确";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	

}
