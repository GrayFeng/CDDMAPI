package com.cdd.mapi.common.uitls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResultUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ResultUtil.class);
	
	public static String getJsonString(Object result){
		String jsonStr = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue
				,SerializerFeature.DisableCircularReferenceDetect);
		logger.info("api-send:" + jsonStr);
		if(AESEncrypter.isDecryption){
			jsonStr = AESEncrypter.encrypt(jsonStr);
		}
		return jsonStr;
	}
}
