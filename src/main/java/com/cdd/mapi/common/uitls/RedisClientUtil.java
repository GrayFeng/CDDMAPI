package com.cdd.mapi.common.uitls;

import java.io.Serializable;
import java.util.ResourceBundle;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Description: RedisClientUtils.java
 * All Rights Reserved.
 * @version 1.0  2014年11月12日 下午4:47:14  
 * @author Gray(jy.feng@zuche.com) 
 */

public class RedisClientUtil {

	private Logger log = LoggerFactory.getLogger(RedisClientUtil.class);
	
	private static RedisClientUtil redisClientUtil;
	
	private final int EX_TIME = 7*24*60*60;
	
	private static JedisPool pool;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException("redis初始化失败，缺少配置文件");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip"),Integer.valueOf(bundle.getString("redis.port")));
	}
	
	private RedisClientUtil(){}
	
	public synchronized static RedisClientUtil getInstance(){
		if(redisClientUtil == null){
			redisClientUtil = new RedisClientUtil();
		}
		return redisClientUtil;
	}
	
	private synchronized Jedis getJedis() throws Exception{
		if(!pool.isClosed()){
			return pool.getResource();
		}else{
			throw new Exception("redis资源池异常关闭");
		}
	}
	
	public void set(String key,Serializable value){
		if(value != null){
			byte[] bytes = SerializationUtils.serialize(value);
			set(key.getBytes(),bytes);
		}
	}
	
	public void set(String key,String value){
		if(StringUtils.isNotEmpty(value)){
			try {
				Jedis jedis = getJedis();
				if(jedis.isConnected()){
					jedis.setex(key,EX_TIME, value);
					pool.returnResource(jedis);
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	
	public void set(byte[] key,byte[] value){
		if(value != null && key != null){
			try{
				Jedis jedis = getJedis();
				if(jedis.isConnected()){
					jedis.setex(key,EX_TIME, value);
					pool.returnResource(jedis);
				}
			}catch(Exception e){
				log.error(e.getMessage(),e);
			}
			
		}
	}
	
	public String get(String key){
		try {
			String value = null;
			Jedis jedis = getJedis();
			value = jedis.get(key);
			pool.returnResource(jedis);
			return value;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	public <T> T getObject(String key){
		T t = null;
		try{
			Jedis jedis = getJedis();
			byte[] value = jedis.get(key.getBytes());
			pool.returnResource(jedis);
			if(value != null && value.length > 0){
				Object obj = SerializationUtils.deserialize(value);
				t = (T)obj;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return t;
	}
	
	public boolean exists(String key){
		boolean result = false;
		try {
			Jedis jedis = getJedis();
			result = jedis.exists(key);
			pool.returnResource(jedis);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	public void del(String key){
		try {
			Jedis jedis = getJedis();
			jedis.del(key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
}
