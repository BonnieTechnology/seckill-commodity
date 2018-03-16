package com.seckill.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.seckill.common.Result;
import com.seckill.common.util.MD5Util;
import com.seckill.common.util.UUIDUtil;
import com.seckill.component.redis.RedisService;
import com.seckill.component.redis.UserPrefix;
import com.seckill.exception.GlobleException;
import com.seckill.mapper.UserMapper;
import com.seckill.pojo.User;
import com.seckill.pojo.vo.LoginVo;
import com.seckill.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	RedisService redisService;

	public static final String COOKIE_USER_TOKEN = "COOKIE_USER_TOKEN";

	@Override
	public Result login(HttpServletResponse response,LoginVo loginVo) {
		//验证  使用了工具类注解判断，以后都不用这些了

		if(loginVo == null) {
			throw new GlobleException(501, "请输入手机号和密码");
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		////通过手机号获取用户信息,判断手机号是否存在
		User user = userMapper.getByMobile(mobile);
		if(user == null) {
			throw new GlobleException(501, "手机号或密码错误");
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobleException(501, "手机号或密码错误");
		}
		//生成cookie,写入到redis中
		String uuid = UUIDUtil.uuid();
		String token = uuid + user.getMobile();
		addCookie(response,token,user);
		return Result.success("登录成功");
	}
	/**
	 * 根据token在redis中取数据
	 */
	@Override
	public User getUserByToken(HttpServletResponse response,String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		User user = redisService.get(UserPrefix.token, token, User.class);
		//增加user的cookie时间
		if(user != null) {
			addCookie(response,token, user);
		}
		return user;
	}

	/**
	 * 设置cookie和redis数据
	 * @param response
	 * @param user
	 */
	private void addCookie(HttpServletResponse response,String token,User user) {
		redisService.set(UserPrefix.token, token, user);
		Cookie cookie = new Cookie(COOKIE_USER_TOKEN, token);
		cookie.setMaxAge(UserPrefix.token.exprieSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public User getById(String mobile) {
		//取缓存
		User user = redisService.get(UserPrefix.getById, mobile, User.class);
		if(user != null) {
			return user;
		}
		user = userMapper.getByMobile(mobile);
		if(user != null) {
			redisService.set(UserPrefix.getById, mobile, user);
		}
		return user;
	}
	
	
	


}
