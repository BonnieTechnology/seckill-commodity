package com.seckill.component.redis;

public class UserPrefix extends RedisBasePrefix {

	
	private static final int COOKIE_EXPRIE = 3600*24;
	
	 public UserPrefix(int exprieSeconds, String prefix) {
		super(exprieSeconds, prefix);
	}

	public static UserPrefix token = new UserPrefix(COOKIE_EXPRIE,"USER_TK_");
	public static UserPrefix getById = new UserPrefix(0,"USER_ID_");

	
	

	
	


}
