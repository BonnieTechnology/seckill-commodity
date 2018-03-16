package com.seckill.common.util;

import java.util.UUID;
/**
 * 生成uuid的工具类
 * @author Administrator
 *
 */
public class UUIDUtil {
	
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	

}
