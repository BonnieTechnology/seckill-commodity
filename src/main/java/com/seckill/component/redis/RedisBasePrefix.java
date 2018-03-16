package com.seckill.component.redis;
/**
 * redis的key前缀的抽象类
 * @author Administrator
 *
 */
public abstract class RedisBasePrefix implements RedisKeyPrefix {
	
	private int exprieSeconds;
	private String prefix;
	
	public RedisBasePrefix(int exprieSeconds, String prefix) {
		this.exprieSeconds = exprieSeconds;
		this.prefix = prefix;
	}

	public RedisBasePrefix(String prefix) {
		this.prefix = prefix;
	}

	
	
	@Override
	public int exprieSeconds() {
		//0表示永不过期
		return exprieSeconds;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

}
