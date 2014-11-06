package com.cdd.mapi.cof.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.mapi.cof.service.ICOFService;
import com.cdd.mapi.common.annotation.NotNeedLogin;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsVO;
import com.cdd.mapi.pojo.ForumAnswerVO;
import com.cdd.mapi.pojo.Member;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:26:49
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api/cof")
public class COFController {
	
	private Logger log = LoggerFactory.getLogger(COFController.class);
	
	@Autowired
	private ICOFService cofService;
	
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("addNews")
	@ResponseBody
	public String addNews(String uid,String params){
		Result result = null;
		try{
			CircleOfFriends cof = JSON.parseObject(params, CircleOfFriends.class);
			Member member = memberService.getMemberByUID(uid);
			if(cof != null && member != null
					&& StringUtils.isNotEmpty(cof.getContent())){
				cof.setMemberId(member.getId());
				cofService.addNews(cof);
				result = Result.getSuccessResult();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，发布失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("reply")
	@ResponseBody
	public String reply(String uid,String params){
		Result result = null;
		try{
			COFReply reply = JSON.parseObject(params, COFReply.class);
			Member member = memberService.getMemberByUID(uid);
			if(reply != null && member != null
					&& StringUtils.isNotEmpty(reply.getMessage())){
				reply.setMemberId(member.getId());
				cofService.addReply(reply);
				result = Result.getSuccessResult();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，回复失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("newsList")
	@ResponseBody
	public String newsList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = jsonObject.getInteger("pageNum");
			Member member = memberService.getMemberByUID(uid);
			List<CofNewsVO> newsList = cofService.getCofNewsLisst(pageNum, member.getId());
			result = Result.getSuccessResult();
			Map<String,Object> map = Maps.newHashMap();
			if(newsList != null ){
				map.put("size", newsList.size());
				map.put("newsList", newsList);
			}else{
				map.put("size", 0);
				map.put("newsList", null);
			}
			result.setRe(map);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的新鲜事");
			}
		}
		return ResultUtil.getJsonString(result);
	}
}
