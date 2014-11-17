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
import com.cdd.mapi.common.enums.EAffiliatedType;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.COFAffiliatedInfo;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.COFReplyVO;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsVO;
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
			List<CofNewsVO> newsList = cofService.getCofNewsList(pageNum, member.getId());
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
	
	@RequestMapping("searchNews")
	@ResponseBody
	public String searchNews(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = jsonObject.getInteger("pageNum");
			String keyword = jsonObject.getString("keyword");
			if(StringUtils.isNotEmpty(keyword)){
				List<CofNewsVO> newsList = cofService.searchNews(pageNum, keyword);
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
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"请输入查询关键字");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的新鲜事");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("favNewsList")
	@ResponseBody
	public String favNewsList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = jsonObject.getInteger("pageNum");
			Member member = memberService.getMemberByUID(uid);
			List<CofNewsVO> newsList = cofService.getFavNewsList(pageNum, member.getId());
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
	
	@RequestMapping("memberNewsList")
	@ResponseBody
	public String memberNewsList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = jsonObject.getInteger("pageNum");
			Integer memberId = jsonObject.getInteger("memberId");
			if(memberId != null){
				List<CofNewsVO> newsList = cofService.getMemberCofNewsList(pageNum, memberId);
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
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,缺少用户ID");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的新鲜事");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("hotNewsList")
	@ResponseBody
	public String hotNewsList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = jsonObject.getInteger("pageNum");
			List<CofNewsVO> newsList = cofService.getHotNewsList(pageNum);
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
	
	@RequestMapping("replyList")
	@ResponseBody
	public String replyList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = jsonObject.getInteger("pageNum");
			Integer newsId = jsonObject.getInteger("cofId");
			if(newsId != null){
				List<COFReplyVO> replyList = cofService.getReplyListByNewsId(newsId, pageNum);
				result = Result.getSuccessResult();
				Map<String,Object> map = Maps.newHashMap();
				if(replyList != null ){
					map.put("size", replyList.size());
					map.put("replyList", replyList);
				}else{
					map.put("size", 0);
					map.put("replyList", null);
				}
				result.setRe(map);
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,动态ID不能为空");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的回复");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("likeNews")
	@ResponseBody
	public String likeNews(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer newsId = jsonObject.getInteger("cofId");
			Member member = memberService.getMemberByUID(uid);
			if(newsId != null && member != null){
				COFAffiliatedInfo affiliatedInfo = new COFAffiliatedInfo();
				affiliatedInfo.setCofId(newsId);
				affiliatedInfo.setType(EAffiliatedType.LIKE.getCode());
				affiliatedInfo.setMemberId(member.getId());
				Integer isDone = cofService.findCofAffiliatedInfo(affiliatedInfo);
				if(isDone == null || isDone == 0){
					cofService.addCofAffiliated(affiliatedInfo);
					result = Result.getSuccessResult();
				}else{
					result = new Result(EEchoCode.ERROR.getCode(),"已经赞过了");
				}
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"缺少动态ID");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("favNews")
	@ResponseBody
	public String favNews(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer newsId = jsonObject.getInteger("cofId");
			Member member = memberService.getMemberByUID(uid);
			if(newsId != null && member != null){
				COFAffiliatedInfo affiliatedInfo = new COFAffiliatedInfo();
				affiliatedInfo.setCofId(newsId);
				affiliatedInfo.setType(EAffiliatedType.FAV.getCode());
				affiliatedInfo.setMemberId(member.getId());
				Integer isDone = cofService.findCofAffiliatedInfo(affiliatedInfo);
				if(isDone == null || isDone == 0){
					cofService.addCofAffiliated(affiliatedInfo);
					result = Result.getSuccessResult();
				}else{
					result = new Result(EEchoCode.ERROR.getCode(),"已经收藏了");
				}
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"缺少动态ID");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("shareNews")
	@ResponseBody
	public String shareNews(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer newsId = jsonObject.getInteger("cofId");
			Member member = memberService.getMemberByUID(uid);
			if(newsId != null && member != null){
				COFAffiliatedInfo affiliatedInfo = new COFAffiliatedInfo();
				affiliatedInfo.setCofId(newsId);
				affiliatedInfo.setType(EAffiliatedType.SHARE.getCode());
				affiliatedInfo.setMemberId(member.getId());
				cofService.addCofAffiliated(affiliatedInfo);
				result = Result.getSuccessResult();
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"缺少动态ID");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("getNewsInfo")
	@ResponseBody
	public String getNewsInfo(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer newsId = jsonObject.getInteger("cofId");
			if(newsId != null){
				CofNewsVO cofNewsVO = cofService.getNewsInfoById(newsId);
				result = Result.getSuccessResult();
				result.setRe(cofNewsVO);
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"缺少动态ID");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常");
			}
		}
		return ResultUtil.getJsonString(result);
	}
}
