package com.cdd.mapi.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class Constant {
	
	public static final Integer MAX_DICE_NUM = 5;
	
	public static final Integer MAX_DICE_POINT = 6;
	
	public static final String HOST_URL = "http://123.57.45.145";
	
	public static final String PHOTO_URL_PATH = HOST_URL + ":82";
	
	public static final String PHOTO_BASE_PATH = "/alidata/www/cdd_images/";
	
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
