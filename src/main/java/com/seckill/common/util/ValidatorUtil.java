package com.seckill.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
/**
 * 验证工具类
 * @author Administrator
 *
 */
public class ValidatorUtil {
	/**
	 * 手机格式正则表达式
	 */
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	/**
	 * 验证手机号方法
	 * @param mobile 需要验证的手机号
	 * @return 
	 */
	public static boolean isMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(mobile);
		return m.matches();
	}
	
//	public static void main(String[] args) {
//			System.out.println(isMobile("18912341234"));
//			System.out.println(isMobile("1891234123"));
//	}
}
