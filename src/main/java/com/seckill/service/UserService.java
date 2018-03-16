package com.seckill.service;

import javax.servlet.http.HttpServletResponse;

import com.seckill.common.Result;
import com.seckill.pojo.User;
import com.seckill.pojo.vo.LoginVo;


public interface UserService {
	
	Result login(HttpServletResponse response,LoginVo user);

	User getUserByToken(HttpServletResponse response,String token);


}
