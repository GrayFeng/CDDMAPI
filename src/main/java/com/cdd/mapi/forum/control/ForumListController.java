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
import com.cdd.mapi.common.annotation.NotNeedLogin;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.forum.service.IForumService;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.ForumAnswerVO;
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
public class ForumListController {
	
	private Logger log = LoggerFactory.getLogger(ForumListController.class);
	
	@Autowired
	private IForumService forumService;
	
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("answerList")
	@ResponseBody
	@NotNeedLogin
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
	@NotNeedLogin
	public String subjectList(String uid,String params){
		Result result = getSubjectList(uid, params,SubjectListType.LIST);
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("hotSubjectList")
	@ResponseBody
	@NotNeedLogin
	public String hotSubjectList(String uid,String params){
		Result result = getSubjectList(uid, params,SubjectListType.HOT_LIST);
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("mySubjectList")
	@ResponseBody
	public String mySubjectList(String uid,String params){
		Result result = getSubjectList(uid, params,SubjectListType.MY_LIST);
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("myAnswerSubjectList")
	@ResponseBody
	public String myAnswerSubjectList(String uid,String params){
		Result result = getSubjectList(uid, params,SubjectListType.MY_ANSWER);
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("favSubjectList")
	@ResponseBody
	public String favSubjectList(String uid,String params){
		Result result = getSubjectList(uid, params,SubjectListType.FAV_LIST);
		return ResultUtil.getJsonString(result);
	}
	
	private enum SubjectListType{
		LIST,HOT_LIST,MY_LIST,MY_ANSWER,FAV_LIST
	}
	
	private Result getSubjectList(String uid,String params,SubjectListType type){
		Result result = null;
		try{
			JSONObject jsonObject = null;
			if(StringUtils.isNotEmpty(params)){
				jsonObject = JSON.parseObject(params);
			}else{
				jsonObject = new JSONObject();
			}
			Integer itemId = jsonObject.getInteger("itemId");
			Integer subItemId = jsonObject.getInteger("subItemId");
			Integer memberId = jsonObject.getInteger("memberId");
			if(memberId == null){
				Member member = memberService.getMemberByUID(uid);
				if(member != null){
					memberId = member.getId();
				}
			}
			Integer pageNum = jsonObject.getInteger("pageNum");
			ForumSubjectSearch forumSubjectSearch = new ForumSubjectSearch();
			forumSubjectSearch.setPageNum(pageNum);
			List<ForumSubjectVO> list = null;
			switch (type) {
			case LIST:
				if((itemId != null)){
					forumSubjectSearch.setItemId(itemId);
					forumSubjectSearch.setSubItemId(subItemId);
					list = forumService.getSubjectList(forumSubjectSearch);
				}
				break;
			case HOT_LIST:
				list = forumService.getHotSubjectList(forumSubjectSearch);
				break;
			case MY_LIST:
				forumSubjectSearch.setMemberId(memberId);
				list = forumService.getSubjectList(forumSubjectSearch);
				break;
			case MY_ANSWER:
				forumSubjectSearch.setMemberId(memberId);
				list = forumService.getMySubjectList(forumSubjectSearch);
				break;
			case FAV_LIST:
				forumSubjectSearch.setMemberId(memberId);
				list = forumService.getFavSubjectList(forumSubjectSearch);
				break;
			default:
				break;
			}
			result = Result.getSuccessResult();
			Map<String,Object> map = Maps.newHashMap();
			if(list != null){
				map.put("size", list.size());
				map.put("subjectList", list);
			}else{
				map.put("size", 0);
				map.put("subjectList", null);
			}
			result.setRe(map);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取失败,未找到相关的提问");
			}
		}
		return result;
	}
}
