package com.cdd.mapi.common.uitls;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public final class CommonValidate {

	/**
	 * 手机规则匹配符
	 */
	private static final Pattern MOBILE = Pattern
			.compile("^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$");

	/**
	 * 邮箱验证匹配符
	 * 
	 */
	private static final Pattern EMAIL = Pattern
			.compile("^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\??\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\~]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\??\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\~]\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3})(\\]?)$");

	private CommonValidate() {
	}

	/**
	 * Description: 手机验证
	 */
	public static boolean checkMobile(String xmobile) {
		if (StringUtils.isBlank(xmobile)) {
			return false;
		}
		if (xmobile.length() > 11) {
			return false;
		}
		return MOBILE.matcher(xmobile).matches();
	}

	/**
	 * Description: 邮箱验证方法
	 */
	public static boolean checkEmail(String xemail) {
		if (StringUtils.isBlank(xemail)) {
			return false;
		}
		if (xemail.length() > 30) {
			return false;
		}
		return EMAIL.matcher(xemail).matches();
	}

	// **这个有时间可以用正则再改下
	public static boolean checkMemberName(String name) {
		if (StringUtils.isBlank(name) || StringUtils.equals(name, "请输入您的真实姓名")) {
			return false;
		}
		boolean flag = true;
		String checkString = "`~!@#$%^&*+-=[]{}\\|;':\",./<>? ";
		for (int j = 0; j < checkString.length(); j++) {
			if (name.indexOf(checkString.substring(j, j + 1)) != -1) {
				flag = false;
				break;
			}
		}
		if (flag && name.matches("^\\d+$")) {
			flag = false;
		}
		if (name.length() > 15) {
			flag = false;
		}
		return flag;
	}
}
