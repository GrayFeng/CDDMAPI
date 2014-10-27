package com.cdd.mapi.common;

import org.springframework.web.context.WebApplicationContext;

public class SpringConstant {
	
	private static WebApplicationContext webApplicationContext;

	public static WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public static void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		SpringConstant.webApplicationContext = webApplicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		if(webApplicationContext != null){
			return (T)webApplicationContext.getBean(beanName);
		}
		return null;
	}
	
}
