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
	
	private static final int REDIS_PORT = 6379;

	private static final String REDIS_HOST = "192.168.118.128";

	private Logger log = LoggerFactory.getLogger(RedisClientUtil.class);
	
	private static RedisClientUtil redisClientUtil;
	
	private static Jedis jedis;
	
	private final int EX_TIME = 7*24*60*60;
	
	private RedisClientUtil(){}
	
	public synchronized static RedisClientUtil getInstance(){
		if(redisClientUtil == null){
			redisClientUtil = new RedisClientUtil();
		}
		return redisClientUtil;
	}
	
	private synchronized Jedis getJedis(){
		if(jedis == null){
			jedis = new Jedis(REDIS_HOST,REDIS_PORT);
		}
		if(!jedis.isConnected()){
			jedis.connect();
		}
		return jedis;
	}
	
	public void set(String key,Serializable value){
		if(value != null){
			byte[] bytes = SerializationUtils.serialize(value);
			set(key.getBytes(),bytes);
		}
	}
	
	public void set(String key,String value){
		if(StringUtils.isNotEmpty(value)){
			Jedis jedis = getJedis();
			if(jedis.isConnected()){
				jedis.setex(key,EX_TIME, value);
			}
		}
	}
	
	public void set(byte[] key,byte[] value){
		if(value != null && key != null){
			Jedis jedis = getJedis();
			if(jedis.isConnected()){
				jedis.setex(key,EX_TIME, value);
			}
		}
	}
	
	public String get(String key){
		return getJedis().get(key);
	}
	
	public <T> T getObject(String key){
		byte[] value = getJedis().get(key.getBytes());
		T t = null;
		if(value != null && value.length > 0){
			Object obj = SerializationUtils.deserialize(value);
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
	
	public static void main(String[] args) {
		Test test = new Test();
		test.setName("123");
		byte[] temp = SerializationUtils.serialize(test);
		Test test1 = (Test)SerializationUtils.deserialize(temp);
		System.out.println(test1.getName());
	}
}
class Test implements Serializable{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
