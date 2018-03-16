package com.seckill.component.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.seckill.pojo.User;
import com.seckill.service.UserService;
import com.seckill.util.validator.UserThreadLocal;


/**
 * 自动定义的User对象参数解析器
 * @author Administrator
 *
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Autowired
	UserService userService;

	/**
	 * 参数的设方法,确定是否是需要的User类型，如果是才执行下个方法
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz == User.class;
	}

	/**
	 * 上面的方法返回true才执行这个方法进行获取数据返回user对象
	 */
	@Override
	public User resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer viewContainer, 
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) {
		return UserThreadLocal.getUser();
	}

}
