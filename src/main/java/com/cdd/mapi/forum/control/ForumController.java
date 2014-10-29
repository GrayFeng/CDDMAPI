package com.cdd.mapi.forum.control;

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
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.forum.service.IForumService;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.ForumAnswer;
import com.cdd.mapi.pojo.ForumAnswerVO;
import com.cdd.mapi.pojo.ForumSubject;
import com.cdd.mapi.pojo.ForumSubjectSearch;
import com.cdd.mapi.pojo.ForumSubjectVO;
import com.cdd.mapi.pojo.Member;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-10-28 下午9:42:25
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api/forum")
public class ForumController {
	
	private Logger log = LoggerFactory.getLogger(ForumController.class);
	
	@Autowired
	private IForumService forumService;
	
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("addSubject")
	@ResponseBody
	public String addSubject(String uid,String params){
		Result result = null;
		try{
			ForumSubject subject = JSON.parseObject(params, ForumSubject.class);
			Member member = memberService.getMemberByUID(uid);
			if(subject != null && member != null
					&& StringUtils.isNotEmpty(subject.getTitle())
					&& StringUtils.isNotEmpty(subject.getContent())
					&& subject.getItemId() != null){
				subject.setMemberId(member.getId());
				forumService.addSubject(subject);
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
	
	@RequestMapping("addAnswer")
	@ResponseBody
	public String addAnswer(String uid,String params){
		Result result = null;
		try{
			ForumAnswer answer = JSON.parseObject(params, ForumAnswer.class);
			Member member = memberService.getMemberByUID(uid);
			if(answer != null && member != null
					&& StringUtils.isNotEmpty(answer.getContent())
					&& answer.getSubjectId() != null){
				answer.setMemberId(member.getId());
				forumService.addAnswer(answer);
				result = Result.getSuccessResult();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，回答失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("subjectInfo")
	@ResponseBody
	public String subjectInfo(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer subjectId = jsonObject.getInteger("subjectId");
			if(subjectId != null){
				ForumSubjectVO subject = forumService.getForumSubjectById(subjectId);
				if(subject != null ){
					result = Result.getSuccessResult();
					result.setRe(subject);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的主题");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("answerList")
	@ResponseBody
	public String answerList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer subjectId = jsonObject.getInteger("subjectId");
			Integer pageNum = jsonObject.getInteger("pageNum");
			if(subjectId != null){
				List<ForumAnswerVO> answerList = forumService.getAnswerListBySubjectId(subjectId,pageNum);
				result = Result.getSuccessResult();
				Map<String,Object> map = Maps.newHashMap();
				if(answerList != null ){
					map.put("size", answerList.size());
					map.put("answerList", answerList);
				}else{
					map.put("size", 0);
					map.put("answerList", null);
				}
				result.setRe(map);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的回答");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("subjectList")
	@ResponseBody
	public String subjectList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer itemId = jsonObject.getInteger("itemId");
			Integer subItemId = jsonObject.getInteger("subItemId");
			Integer pageNum = jsonObject.getInteger("pageNum");
			if(itemId != null && pageNum != null){
				ForumSubjectSearch forumSubjectSearch = new ForumSubjectSearch();
				forumSubjectSearch.setPageNum(pageNum);
				forumSubjectSearch.setItemId(itemId);
				forumSubjectSearch.setSubItemId(subItemId);
				List<ForumSubjectVO> list = forumService.getSubjectList(forumSubjectSearch);
				result = Result.getSuccessResult();
				Map<String,Object> map = Maps.newHashMap();
				if(list != null ){
					map.put("size", list.size());
					map.put("subjectList", list);
				}else{
					map.put("size", 0);
					map.put("subjectList", null);
				}
				result.setRe(map);
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败，缺少参数");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的回答");
			}
		}
		return ResultUtil.getJsonString(result);
	}

}