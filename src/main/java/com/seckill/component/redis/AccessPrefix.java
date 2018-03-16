package com.seckill.component.redis;

public class AccessPrefix extends RedisBasePrefix {

	
	 public AccessPrefix(int exprieSeconds, String prefix) {
		super(exprieSeconds, prefix);
	}

	 
	 public static AccessPrefix withExpire(int exprieSeconds) {
		 return new AccessPrefix(exprieSeconds, "access");
	 }

}
