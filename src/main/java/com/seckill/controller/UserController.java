package com.seckill.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.common.Result;
import com.seckill.pojo.vo.LoginVo;
import com.seckill.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	public String longin() {
		return "login";
	}
	
	@RequestMapping("/dologin")
	@ResponseBody
	public Result dolongin(HttpServletResponse response, @Valid LoginVo user) {
		return userService.login(response,user);
	}
	
	
}
