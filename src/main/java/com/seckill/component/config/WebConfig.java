package com.seckill.component.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.seckill.util.validator.AccessInterceptor;

/**
 * 将自定义的类的使用框架添加到自动注入
 * @author Administrator
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	UserArgumentResolver userArgumentResolver;
	/**
	 * 将获取的user对象添加到自动注入
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}
	
	@Autowired
	AccessInterceptor accessInterceptor;
	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(accessInterceptor);
		ir.excludePathPatterns("/user/**");
	}
	
	
}
