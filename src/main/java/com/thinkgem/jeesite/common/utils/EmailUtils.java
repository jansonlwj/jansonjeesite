/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email工具类
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
public class EmailUtils {

	/**
	 * 验证邮箱是否合法
	 */
	public static boolean validateEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	

}
