package com.seckill.util.validator;

import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.seckill.component.redis.AccessPrefix;
import com.seckill.component.redis.RedisService;
import com.seckill.pojo.User;
import com.seckill.service.UserService;
import com.seckill.service.impl.UserServiceImpl;

/**
 * 拦截器，用于频繁访问的拦截
 * @author Administrator
 *
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			User user = getUser(request,response);
			if(user == null) {
				response.setStatus(302);
				response.setHeader("Location", "/user/login");
				return false;
			}
			UserThreadLocal.setUser(user);
			HandlerMethod hm = (HandlerMethod) handler;
			AccessLimit limit = hm.getMethodAnnotation(AccessLimit.class);
			if(limit == null) {
				return true;
			}
			int senconds = limit.seconds();
			int maxCount = limit.maxCount();
			boolean needLogin = limit.needLogin();
			if(needLogin) {
				if(user == null) {
					response.setStatus(302);
					response.setHeader("Location", "/user/login");
					return false;
				}
			}
			
			Integer count = redisService.get(AccessPrefix.withExpire(senconds), ""+user.getId(), Integer.class);
			if(count == null) {
				redisService.set(AccessPrefix.withExpire(senconds), ""+user.getId(), 1);
			}else if(count < maxCount){
				redisService.incr(AccessPrefix.withExpire(senconds),""+user.getId());
			}else {
				render(response,"您访问太频繁！请稍后再试！");
				return false;
			}
		}
		
		return super.preHandle(request, response, handler);
	}
	
	private void render(HttpServletResponse response, String string) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		out.write(string.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	private User getUser(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(UserServiceImpl.COOKIE_USER_TOKEN);
		String cookieToken = getCookieValut(request,UserServiceImpl.COOKIE_USER_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return userService.getUserByToken(response,token);
	}
	private String getCookieValut(HttpServletRequest request, String cookieUserToken) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieUserToken)) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
