package com.cdd.mapi.common.uitls;

import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;


/**
 * Description: RedisClientUtils.java
 * All Rights Reserved.
 * @version 1.0  2014年11月12日 下午4:47:14  
 * @author Gray(jy.feng@zuche.com) 
 */

public class RedisClientUtil {
	
	private Logger log = LoggerFactory.getLogger(RedisClientUtil.class);
	
	private static RedisClientUtil redisClientUtil;
	
	private static Jedis jedis;
	
	private RedisClientUtil(){}
	
	public synchronized static RedisClientUtil getInstance(){
		if(redisClientUtil == null){
			redisClientUtil = new RedisClientUtil();
		}
		return redisClientUtil;
	}
	
	private synchronized Jedis getJedis(){
		if(jedis == null){
			jedis = new Jedis("192.168.118.128",6379);
		}
		if(!jedis.isConnected()){
			jedis.connect();
		}
		return jedis;
	}
	
	public void set(String key,Serializable value){
		if(value != null){
			byte[] bytes = SerializationUtils.serialize(value);
			set(key, new String(bytes));
		}
	}
	
	public void set(String key,String value){
		if(StringUtils.isNotEmpty(value)){
			Jedis jedis = getJedis();
			if(jedis.isConnected()){
				jedis.setex(key,7*24*60*60, value);
			}
		}
	}
	
	public String get(String key){
		return getJedis().get(key);
	}
	
	public <T> T getObject(String key){
		String value = getJedis().get(key);
		T t = null;
		if(StringUtils.isNotEmpty(value)){
			Object obj = SerializationUtils.deserialize(value.getBytes());
			try{
				t = (T)obj;
			}catch(Exception e){
				log.error(e.getMessage(),e);
			}
		}
		return t;
	}
	
	public boolean exists(String key){
		return getJedis().exists(key);
	}
	
	public void del(String key){
		getJedis().del(key);
	}
}
