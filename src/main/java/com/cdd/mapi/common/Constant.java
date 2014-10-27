package com.cdd.mapi.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class Constant {
	
	public static final Integer MAX_DICE_NUM = 5;
	
	public static final Integer MAX_DICE_POINT = 6;
	
	public static final String HOST_URL = "http://www.yedianshaiwang.com";
	
	public static final String PHOTO_URL_PATH = HOST_URL + "/api/img.do?key=";
	
	public static final String PRIZE_PHOTO_URL_PATH = HOST_URL + "/api/prize/img.do?key=";
	
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

}
