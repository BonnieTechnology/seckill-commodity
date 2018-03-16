package com.seckill.component.redis;

public interface RedisKeyPrefix {
	//过期时间
	int exprieSeconds();
	//设置前缀
	String getPrefix();
	
	
}
