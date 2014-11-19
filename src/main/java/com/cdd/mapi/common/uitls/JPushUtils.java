package com.cdd.mapi.common.uitls;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.google.common.collect.Lists;

/**
 * Description: Test.java
 * All Rights Reserved.
 * @version 1.0  2014年10月24日 下午3:52:16  
 * @author Gray(jy.feng@zuche.com) 
 */

public class JPushUtils {
	
	private Logger log = LoggerFactory.getLogger(JPushUtils.class);
	
	private final static String MASTER_SECRET = "9f3fc8782d2c461f879bbc49";
	
	private final static String APP_KEY = "46d93ea2b7edc1f0f726f938";
	
	private static JPushClient jpushClient ;
	
	private static JPushUtils jPushUtils ;
	
	private JPushUtils(){}
	
	public synchronized static JPushUtils getInstance(){
		if(jPushUtils == null){
			jPushUtils = new JPushUtils();
		}
		return jPushUtils;
	}
	
	private synchronized JPushClient getClient(){
		if(jpushClient == null){
			jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 3);
		}
		return jpushClient;
	}
	
	public boolean sendMsg(List<String> aliasList,String msg){
		if(aliasList !=null && aliasList.size()>0){
			PushPayload pushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(aliasList))
					.setNotification(Notification.alert(msg)).build();
			try {
				JPushClient jpushClient = JPushUtils.getInstance().getClient();
				PushResult result = jpushClient.sendPush(pushPayload);
				if(result != null){
					return result.isResultOK();
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return false;
	}
	
	public boolean sendMsg(String alias,String msg){
		if(StringUtils.isNotEmpty(alias)){
			PushPayload pushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.alert(msg)).build();
			try {
				JPushClient jpushClient = JPushUtils.getInstance().getClient();
				PushResult result = jpushClient.sendPush(pushPayload);
				if(result != null){
					return result.isResultOK();
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		return false;
	}
	
	public boolean sendMsgAll(String msg){
		try {
			PushPayload pushPayload = PushPayload.alertAll(msg);
			JPushClient jpushClient = JPushUtils.getInstance().getClient();
			PushResult result = jpushClient.sendPush(pushPayload);
			if(result != null){
				return result.isResultOK();
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	public static void main(String[] args) {
		List<String> aliasList = Lists.newArrayList();
		aliasList.add("gray");
		JPushUtils.getInstance().sendMsg(aliasList, "123");
	}
}
