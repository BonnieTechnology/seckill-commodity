package com.seckill.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 使用jedis提供服务的类
 * @author Administrator
 *
 */
@Service
public class RedisService {

	@Autowired
	JedisPool jedisPool;
	private String str;
	/**
	 * 通过key获取数据
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(RedisKeyPrefix prefixKey,String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			String realKey = prefixKey.getPrefix() + key;
			jedis = jedisPool.getResource();
			String str = jedis.get(realKey);
			//将str转化为传入的类型
			T t = StringToBean(str, clazz);
			return t;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}


	/**
	 * 通过k-v将数据添加到redis中
	 * @param key 保存的键值
	 * @param value 保存的对象
	 * @return 返回turn是保存成功，false是失败
	 */
	public <T> boolean set(RedisKeyPrefix prefixKey,String key, T value) {
		if(key == null && key.length() <= 0) {
			return false;
		}
		Jedis jedis = null;
		String realKey = prefixKey.getPrefix() + key;
		int seconds = prefixKey.exprieSeconds();
		try {
			jedis = jedisPool.getResource();
			str = beanToString(value);
			if(str == null && str.length() <= 0) {
				return false;
			}
			if(seconds <= 0) {
				jedis.set(realKey, str);
			}else {
				jedis.setex(realKey,seconds, str);
			}
			return true;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 将set到redis的对象转化成string
	 * @param value 需要转化的对象
	 * @return 返回string字符串
	 */
	public static <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class ) {
			return ""+value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else if(clazz == String.class) {
			return (String) value;
		}else {
			return JSON.toJSONString(value);
		}
	}

	/**
	 * 将str转化为对应的类型
	 * @param str 获取的string内容
	 * @param clazz 传入的对象类型
	 * @return 返回java对象
	 */
	@SuppressWarnings("unchecked")
	public static <T>T StringToBean(String str, Class<T> clazz) {
		if(str == null || str.length() <= 0 || clazz == null) {
			return null;
		}
		if(clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		}else if(clazz == String.class ) {
			return (T) str;
		}else if(clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		}else {
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}


	public boolean delete(RedisKeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			long ret =  jedis.del(realKey);
			return ret > 0;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}


	public Long decr(ItemPrefix stock, String string) {
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			//生成真正的key
			String realKey  = stock.getPrefix() + string;
			return  jedis.decr(realKey);
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}


	public Long incr(AccessPrefix access, String key) {
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			//生成真正的key
			String realKey  = access.getPrefix() + key;
			return  jedis.incr(realKey);
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}






}
