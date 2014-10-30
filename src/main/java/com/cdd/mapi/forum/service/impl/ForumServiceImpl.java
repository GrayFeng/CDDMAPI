package com.cdd.mapi.forum.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.mapi.common.pojo.Page;
import com.cdd.mapi.forum.dao.IForumDao;
import com.cdd.mapi.forum.service.IForumService;
import com.cdd.mapi.pojo.ForumAnswer;
import com.cdd.mapi.pojo.ForumAnswerSearch;
import com.cdd.mapi.pojo.ForumAnswerVO;
import com.cdd.mapi.pojo.ForumSubject;
import com.cdd.mapi.pojo.ForumSubjectSearch;
import com.cdd.mapi.pojo.ForumSubjectVO;
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
	
	@Autowired
	private IForumDao forumDao;
	
	@Transactional
	public void addSubject(ForumSubject forumSubject){
		forumDao.addSubject(forumSubject);
		addPhotos(forumSubject.getPhotos(), forumSubject.getId(), null);
	}

	@Override
	@Transactional
	public void addAnswer(ForumAnswer answer) {
		forumDao.addAnswer(answer);
		addPhotos(answer.getPhotos(), null, answer.getId());
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
	public Integer getSubjectCount(ForumSubjectSearch params) {
		return forumDao.getSubjectCount(params);
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
					ForumSubjectVO subjectVO = new ForumSubjectVO();
					subjectVO.setId(getIntegerFromMap(mapInfo,"id"));
					subjectVO.setTitle((String)mapInfo.get("title"));
					subjectVO.setTitle((String)mapInfo.get("content"));
					subjectVO.setLikeCount(getIntegerFromMap(mapInfo,"likeCount"));
					subjectVO.setShareCount(getIntegerFromMap(mapInfo,"shareCount"));
					subjectVO.setFavoriteCount(getIntegerFromMap(mapInfo,"favoriteCount"));
					subjectVO.setMemberId(getIntegerFromMap(mapInfo,"memberId"));
					subjectVO.setItemId(getIntegerFromMap(mapInfo,"itemId"));
					subjectVO.setSubItemId(getIntegerFromMap(mapInfo,"subItemId"));
					subjectVO.setAnonymous(getIntegerFromMap(mapInfo,"anonymous"));
					subjectVO.setMemberPhoto((String)mapInfo.get("photo"));
					subjectVO.setCreateTime((String)mapInfo.get("createTime"));
					Integer answerId = getIntegerFromMap(mapInfo,"answerId");
					if(answerId != null){
						ForumAnswerVO answerVO = new ForumAnswerVO();
						answerVO.setId(answerId);
						answerVO.setContent((String)mapInfo.get("answerContent"));
						answerVO.setMemberId(getIntegerFromMap(mapInfo,"answerMemberId"));
						answerVO.setLikeCount(getIntegerFromMap(mapInfo,"answerlikeCount"));
						answerVO.setCreateTime((String)mapInfo.get("answerCreateTime"));
						answerVO.setMemberName((String)mapInfo.get("answerMemberName"));
						answerVO.setAnonymous(getIntegerFromMap(mapInfo,"answerAnonymous"));
						answerVO.setMemberPhoto((String)mapInfo.get("answerMemberPhoto"));
						subjectVO.setAnswerList(Lists.newArrayList(answerVO));
					}
					list.add(subjectVO);
				}
			}
		}
		return list;
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


}
