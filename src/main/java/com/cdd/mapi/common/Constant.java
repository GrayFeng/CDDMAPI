package com.cdd.mapi.common;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class Constant {
	
	public static String HOST_URL = "http://123.57.45.145";
	
	public static String PHOTO_URL_PATH = HOST_URL + ":82";
	
	public static String PHOTO_BASE_PATH = "/alidata/www/cdd_images/";
	
	public static boolean ENABLE_REDIS = false;
	
	public static boolean IS_DECRYPTION = false;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		if (bundle == null) {
			throw new IllegalArgumentException("初始化失败，缺少配置文件");
		}
		ENABLE_REDIS = Boolean.valueOf(bundle.getString("redis.enable"));
		
		IS_DECRYPTION = Boolean.valueOf(bundle.getString("decryption.enable"));
	}
	
	public static String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static int isSignIn(Date lastSignTime){
		if(lastSignTime != null){
			Date now = new Date();
			String lastSignInDate = DateFormatUtils.format(lastSignTime, "yyyy-dd-MM");
			String nowDate = DateFormatUtils.format(now, "yyyy-dd-MM");
			if(now.before(lastSignTime) 
					|| nowDate.equals(lastSignInDate)){
				return 1;
			}
		}
		return 0;
	}
}
