package com.cdd.mapi.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Description: InitListener.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午11:26:42  
 * @author Gray(jy.feng@zuche.com) 
 */

public class InitListener implements ServletContextListener{
	  public void contextInitialized(ServletContextEvent context){
	    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context.getServletContext());
	    SpringConstant.setWebApplicationContext(wac);
	  }
	
	  public void contextDestroyed(ServletContextEvent sce){
	  }
}
