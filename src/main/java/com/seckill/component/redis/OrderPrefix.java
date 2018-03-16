package com.seckill.component.redis;

public class OrderPrefix extends RedisBasePrefix {

	
	private static final int COOKIE_EXPRIE = 60;
	
	 public OrderPrefix(int exprieSeconds, String prefix) {
		super(exprieSeconds, prefix);
	}

	public static OrderPrefix order = new OrderPrefix(0,"order_");

	
	

	
	


}
