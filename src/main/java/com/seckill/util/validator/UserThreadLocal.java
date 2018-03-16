package com.seckill.util.validator;

import com.seckill.pojo.User;

public class UserThreadLocal {
	
	private static ThreadLocal<User> threadLocal = new ThreadLocal<User>();
	
	public static void setUser(User user) {
		threadLocal.set(user);
	}
	
	public static User getUser() {
		return threadLocal.get();
	}
}
