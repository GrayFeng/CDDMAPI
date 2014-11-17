package com.cdd.mapi.base.control;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.annotation.NotNeedLogin;
import com.cdd.mapi.common.annotation.NotNeedUID;
import com.cdd.mapi.common.cache.MemberCache;
import com.cdd.mapi.common.pojo.LoginInfo;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.pojo.VersionInfo;
import com.google.common.collect.Maps;

/**
 * Description: BaseController.java
 * All Rights Reserved.
 * @version 1.0  2014年10月27日 下午3:21:26  
 * @author Gray(jy.feng@zuche.com) 
 */

@Controller
@RequestMapping("/api/base")
public class BaseController {
	
	@Autowired
	private IBaseService baseService;
	
	@RequestMapping("getCityList")
	@ResponseBody
	@NotNeedLogin
	@NotNeedUID
	public String getCityList(){
		Result result = Result.getSuccessResult();
		result.setRe(baseService.getCityList());
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("provinceList")
	@ResponseBody
	@NotNeedLogin
	@NotNeedUID
	public String getProvinceList(){
		Result result = Result.getSuccessResult();
		result.setRe(baseService.getProvinceList());
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("memberLevelList")
	@ResponseBody
	@NotNeedLogin
	@NotNeedUID
	public String getMemberLevelList(){
		Result result = Result.getSuccessResult();
		result.setRe(baseService.getMemberLevelList());
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("start")
	@ResponseBody
	@NotNeedLogin
	@NotNeedUID
	public String start(String uid,HttpServletRequest request){
		Result result = Result.getSuccessResult();
		Map<String,Object> resultMap = Maps.newHashMap();
		if(StringUtils.isEmpty(uid) 
				|| !MemberCache.getInstance().isHave(uid)){
			uid = "m-"+UUID.randomUUID().toString();
			LoginInfo loginInfo = new LoginInfo();
			MemberCache.getInstance().add(uid, loginInfo);
		}
		resultMap.put("uid", uid);
		resultMap.put("sysTime", System.currentTimeMillis());
		result.setRe(resultMap);
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("forumItemList")
	@ResponseBody
	@NotNeedLogin
	@NotNeedUID
	public String getForumItemList(){
		Result result = Result.getSuccessResult();
		result.setRe(baseService.getForumItemList());
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("examItemList")
	@ResponseBody
	@NotNeedLogin
	@NotNeedUID
	public String getExamItemList(){
		Result result = Result.getSuccessResult();
		result.setRe(baseService.getExamItemList());
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("checkVersion")
	@ResponseBody
	@NotNeedLogin
	public String checkVersion(String uid,String params){
		Result result = null;
		Map<String,Object> resultMap = Maps.newHashMap();
		if(StringUtils.isNotEmpty(params)){
			JSONObject jsonObject = JSON.parseObject(params);
			String cid = jsonObject.getString("cid");
			VersionInfo versionInfo = baseService.checkVersion(cid);
			if(versionInfo != null && versionInfo.isUpgrade()){
				result = Result.getSuccessResult();
				resultMap.put("address", versionInfo.getAddress());
				resultMap.put("ver",versionInfo.getVersion_name());
				resultMap.put("msg", versionInfo.getMsg());
				resultMap.put("upgrade",1);
				result.setRe(resultMap);
			}
		}
		if(result == null){
			result = Result.getSuccessResult();
			resultMap.put("address",null);
			resultMap.put("ver",null);
			resultMap.put("msg","无可更新版本");
			resultMap.put("upgrade",0);
			result.setRe(resultMap);
			
		}
		return ResultUtil.getJsonString(result);
	}

}
