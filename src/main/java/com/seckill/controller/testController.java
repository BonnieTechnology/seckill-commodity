package com.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.common.Result;
import com.seckill.component.redis.RedisService;
import com.seckill.pojo.User;
import com.seckill.service.UserService;


@Controller
@RequestMapping("/user")
public class testController {
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	UserService userService;
	
	
	
	@RequestMapping("/info")
	@ResponseBody
	public Result info(Model model,User user ) {
		return Result.success(user);
	}
	
	
}
