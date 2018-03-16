package com.seckill.common.util;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * md5加密工具类
 * @author Administrator
 *
 */
public class MD5Util {
	
	/**
	 * 使用工具类进行MD5加密
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}
	//设置固定的盐值
	private static final String salt = "1a2b3c4d5f";
	
	/**
	 * 添加固定颜值
	 * @param inputPass
	 * @return
	 */
	public static String inputPassFormPass(String inputPass) {
		String str = "" + salt.charAt(0) + salt.charAt(3) + inputPass + salt.charAt(6) + salt.charAt(2);
		return md5(str);
	}
	
	/**
	 * 使用随机的颜值加密
	 * @param formPass
	 * @param salt
	 * @return
	 */
	public static String formPassToDBPass(String formPass,String salt) {
		String str = "" + salt.charAt(0) + salt.charAt(3) + formPass + salt.charAt(6) + salt.charAt(2);
		return md5(str);
	}
	/**
	 * 生成存入数据库的密码
	 * @param input
	 * @param salt
	 * @return
	 */
	public static String inputPassToDBPass(String input, String salt) {
		String formPass = inputPassFormPass(input);
		return formPassToDBPass(formPass, salt);
	}
	
}
