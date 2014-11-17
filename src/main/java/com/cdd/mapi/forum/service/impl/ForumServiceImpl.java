package com.cdd.mapi.forum.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cdd.mapi.common.enums.EAffiliatedType;
import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.common.pojo.Page;
import com.cdd.mapi.forum.dao.IForumDao;
import com.cdd.mapi.forum.service.IForumService;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.ForumAffiliatedInfo;
import com.cdd.mapi.pojo.ForumAnswer;
import com.cdd.mapi.pojo.ForumAnswerSearch;
import com.cdd.mapi.pojo.ForumAnswerVO;
import com.cdd.mapi.pojo.ForumSubject;
import com.cdd.mapi.pojo.ForumSubjectSearch;
import com.cdd.mapi.pojo.ForumSubjectVO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-10-28 下午9:41:27
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class ForumServiceImpl implements IForumService{
	
	private Logger log = LoggerFactory.getLogger(ForumServiceImpl.class);
	
	@Autowired
	private IForumDao forumDao;
	@Autowired
	private IMemberService memberService;
	
	private final LoadingCache<String, List<?>> searchDataCache = CacheBuilder
			.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES)
			.maximumSize(600).build(new CacheLoader<String, List<?>>() {
				@Override
				public List<?> load(String key) throws Exception {
					return searchSubjectFromCache(key);
				}
		});
	
	@Transactional
	public void addSubject(ForumSubject forumSubject){
		forumDao.addSubject(forumSubject);
		addPhotos(forumSubject.getPhotos(), forumSubject.getId(), null);
		memberService.addMemberScore(forumSubject.getMemberId(), EScoreRuleType.ADD_SUBJECT);
	}

	@Override
	@Transactional
	public void addAnswer(ForumAnswer answer) {
		forumDao.addAnswer(answer);
		addPhotos(answer.getPhotos(), null, answer.getId());
		memberService.addMemberScore(answer.getMemberId(), EScoreRuleType.ANSWER_QUESTION);
	}

	@Override
	public ForumSubjectVO getForumSubjectById(Integer id) {
		return forumDao.getForumSubjectById(id);
	}

	@Override
	public List<ForumAnswerVO> getAnswerListBySubjectId(Integer subjectId,Integer pageNum) {
		List<ForumAnswerVO> list = null;
		Integer prizeCount = forumDao.getAnswerCountBySubjectId(subjectId);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			if(page.getTotalPages() >= pageNum){
				ForumAnswerSearch forumAnswerSearch = new ForumAnswerSearch();
				forumAnswerSearch.setStartNum(page.getStartNum());
				forumAnswerSearch.setSize(page.getSize());
				forumAnswerSearch.setSubjectId(subjectId);
				list = forumDao.getAnswerListBySubjectId(forumAnswerSearch);
			}
		}
		return list;
	}

	@Override
	public List<ForumSubjectVO> getSubjectList(ForumSubjectSearch params) {
		List<ForumSubjectVO> list = null;
		Integer prizeCount = forumDao.getSubjectCount(params);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			Integer pageNum = params.getPageNum() == null ? 1 : params.getPageNum();
			page.setNumber(pageNum);
			params.setStartNum(page.getStartNum());
			params.setSize(page.getSize());
			if(page.getTotalPages() < pageNum){
				return list;
			}
			List<Map<String,Object>> mapList = forumDao.getSubjectList(params);
			if(mapList != null && !mapList.isEmpty()){
				list = Lists.newArrayList();
				for(Map<String,Object> mapInfo : mapList){
					ForumSubjectVO subjectVO = packageSubjectVo(mapInfo);
					list.add(subjectVO);
				}
			}
		}
		return list;
	}
	
	private ForumSubjectVO packageSubjectVo(Map<String,Object> subjectMap){
		ForumSubjectVO subjectVO = new ForumSubjectVO();
		subjectVO.setId(getIntegerFromMap(subjectMap,"id"));
		subjectVO.setTitle((String)subjectMap.get("title"));
		subjectVO.setContent((String)subjectMap.get("content"));
		subjectVO.setLikeCount(getIntegerFromMap(subjectMap,"likeCount"));
		subjectVO.setShareCount(getIntegerFromMap(subjectMap,"shareCount"));
		subjectVO.setFavoriteCount(getIntegerFromMap(subjectMap,"favoriteCount"));
		subjectVO.setMemberId(getIntegerFromMap(subjectMap,"memberId"));
		subjectVO.setMemberName((String)subjectMap.get("memberName"));
		subjectVO.setMemberPhoto((String)subjectMap.get("photo"));
		subjectVO.setItemId(getIntegerFromMap(subjectMap,"itemId"));
		subjectVO.setSubItemId(getIntegerFromMap(subjectMap,"subItemId"));
		subjectVO.setAnonymous(getIntegerFromMap(subjectMap,"anonymous"));
		subjectVO.setCreateTime((String)subjectMap.get("createTime"));
		subjectVO.setMemberSex(getIntegerFromMap(subjectMap,"memberSex"));
		subjectVO.setMemberLevelName((String)subjectMap.get("memberLevelName"));
		Integer answerId = getIntegerFromMap(subjectMap,"answerId");
		if(answerId != null){
			ForumAnswerVO answerVO = new ForumAnswerVO();
			answerVO.setId(answerId);
			answerVO.setContent((String)subjectMap.get("answerContent"));
			answerVO.setMemberId(getIntegerFromMap(subjectMap,"answerMemberId"));
			answerVO.setLikeCount(getIntegerFromMap(subjectMap,"answerlikeCount"));
			answerVO.setCreateTime((String)subjectMap.get("answerCreateTime"));
			answerVO.setMemberName((String)subjectMap.get("answerMemberName"));
			answerVO.setAnonymous(getIntegerFromMap(subjectMap,"answerAnonymous"));
			answerVO.setMemberPhoto((String)subjectMap.get("answerMemberPhoto"));
			answerVO.setMemberSex(getIntegerFromMap(subjectMap,"answerMemberSex"));
			answerVO.setMemberLevelName((String)subjectMap.get("answerMemberLevelName"));
			subjectVO.setAnswerList(Lists.newArrayList(answerVO));
		}
		return subjectVO;
	}
	
	private Integer getIntegerFromMap(Map<String,Object> map,String key){
		Object obj = map.get(key);
		if(obj != null && obj instanceof Integer){
			return (Integer)obj;
		}
		return null;
	}

	@Override
	public Integer getAnswerCountBySubjectId(Integer subjectId) {
		return forumDao.getAnswerCountBySubjectId(subjectId);
	}

	@Override
	public void addPhotos(List<String> photos, Integer subjectId,
			Integer answerId) {
		if(photos != null && !photos.isEmpty() 
				&& (subjectId != null || answerId != null)){
			Map<String,Object> map = Maps.newHashMap();
			map.put("subjectId", subjectId);
			map.put("answerId",answerId);
			map.put("photos",photos);
			forumDao.addPhotos(map);
		}
	}

	@Override
	public List<ForumSubjectVO> getHotSubjectList(ForumSubjectSearch params) {
		List<ForumSubjectVO> list = null;
		Integer prizeCount = forumDao.getHotSubjectCount(params);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			Integer pageNum = params.getPageNum() == null ? 1 : params.getPageNum();
			page.setNumber(pageNum);
			params.setStartNum(page.getStartNum());
			params.setSize(page.getSize());
			if(page.getTotalPages() < pageNum){
				return list;
			}
			List<Map<String,Object>> mapList = forumDao.getHotSubjectList(params);
			if(mapList != null && !mapList.isEmpty()){
				list = Lists.newArrayList();
				for(Map<String,Object> mapInfo : mapList){
					ForumSubjectVO subjectVO = packageSubjectVo(mapInfo);
					list.add(subjectVO);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<ForumSubjectVO> getMySubjectList(ForumSubjectSearch params) {
		List<ForumSubjectVO> list = null;
		Integer prizeCount = forumDao.getMySubjectCount(params);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			Integer pageNum = params.getPageNum() == null ? 1 : params.getPageNum();
			page.setNumber(pageNum);
			params.setStartNum(page.getStartNum());
			params.setSize(page.getSize());
			if(page.getTotalPages() < pageNum){
				return list;
			}
			List<Map<String,Object>> mapList = forumDao.getMySubjectList(params);
			if(mapList != null && !mapList.isEmpty()){
				list = Lists.newArrayList();
				for(Map<String,Object> mapInfo : mapList){
					ForumSubjectVO subjectVO = packageSubjectVo(mapInfo);
					list.add(subjectVO);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<ForumSubjectVO> getFavSubjectList(ForumSubjectSearch params) {
		List<ForumSubjectVO> list = null;
		Integer prizeCount = forumDao.getFavSubjectCount(params);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			Integer pageNum = params.getPageNum() == null ? 1 : params.getPageNum();
			page.setNumber(pageNum);
			params.setStartNum(page.getStartNum());
			params.setSize(page.getSize());
			if(page.getTotalPages() < pageNum){
				return list;
			}
			List<Map<String,Object>> mapList = forumDao.getFavSubjectList(params);
			if(mapList != null && !mapList.isEmpty()){
				list = Lists.newArrayList();
				for(Map<String,Object> mapInfo : mapList){
					ForumSubjectVO subjectVO = packageSubjectVo(mapInfo);
					list.add(subjectVO);
				}
			}
		}
		return list;
	}

	@Override
	@Transactional
	public void addForumAffiliated(ForumAffiliatedInfo forumAffiliatedInfo) {
		if(forumAffiliatedInfo.getType() != null){
			if(forumAffiliatedInfo.getQuestionId() != null){
				forumDao.addForumAffiliated(forumAffiliatedInfo);
				if(EAffiliatedType.LIKE.getCode().equals(forumAffiliatedInfo.getType())){
					forumDao.updateSubjectLikeCount(forumAffiliatedInfo.getQuestionId());
					memberService.addMemberScore(forumAffiliatedInfo.getMemberId(), EScoreRuleType.LIKE_SUBJECT);
				}else if(EAffiliatedType.FAV.getCode().equals(forumAffiliatedInfo.getType())){
					forumDao.updateSubjectFavCount(forumAffiliatedInfo.getQuestionId());
				}else if(EAffiliatedType.SHARE.getCode().equals(forumAffiliatedInfo.getType())){
					forumDao.updateSubjectShareCount(forumAffiliatedInfo.getQuestionId());
					memberService.addMemberScore(forumAffiliatedInfo.getMemberId(), EScoreRuleType.SHARE_SUBJECT);
				}
			}else if(forumAffiliatedInfo.getAnswerId() != null){
				forumDao.addForumAffiliated(forumAffiliatedInfo);
				forumDao.updateAnswerLikeCount(forumAffiliatedInfo.getAnswerId());
			}
		}
	}

	@Override
	public Integer findForumAffiliatedInfo(
			ForumAffiliatedInfo forumAffiliatedInfo) {
		return forumDao.findForumAffiliatedInfo(forumAffiliatedInfo);
	}

	@Override
	public List<ForumSubjectVO> searchSubject(ForumSubjectSearch params) {
		List<ForumSubjectVO> list = null;
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("keyword", params.getKeyword());
		paramsMap.put("itemId", params.getItemId());
		paramsMap.put("subItemId", params.getSubItemId());
		List<Map<String, Object>> mapList = null;
		try {
			mapList = (List<Map<String,Object>>)searchDataCache.get(JSON.toJSONString(paramsMap));
		} catch (ExecutionException e) {
			log.error(e.getMessage(), e);
		}
		if(mapList != null){
			Integer prizeCount = mapList.size();
			if(prizeCount != null && prizeCount > 0){
				Page page = new Page();
				page.setTotal(prizeCount);
				page.setSize(20);
				Integer pageNum = params.getPageNum() == null ? 1 : params.getPageNum();
				page.setNumber(pageNum);
				if(page.getTotalPages() < pageNum){
					return list;
				}
				list = Lists.newArrayList();
				for(int i = 0;i < page.getSize() ; i++){
					int index = page.getStartNum() + i;
					if(index < mapList.size()){
						Map<String,Object> mapInfo = mapList.get(index);
						ForumSubjectVO subjectVO = packageSubjectVo(mapInfo);
						list.add(subjectVO);
					}else{
						break;
					}
				}
			}
		}
		return list;
	}
	
	private List<Map<String,Object>> searchSubjectFromCache(String params){
		ForumSubjectSearch subjectSearch = JSON.parseObject(params, ForumSubjectSearch.class);
		return forumDao.searchSubject(subjectSearch);
	}

}
