package com.seckill.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * jedisPool创建工厂
 * @author Administrator
 *
 */

@Service
public class JedisFactory {
	
	@Autowired
	RedisConfig redisConfig;
	
	//将对象交给springboot管理
	@Bean
	public JedisPool jedisPoolFactory() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(redisConfig.getPoolMaxIdle());
		config.setMaxTotal(redisConfig.getPoolMaxTotal());
		config.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
		//获取jedisPool对象
		JedisPool pool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(), redisConfig.getPassword(), 0);
		return pool;
	}
	

}
