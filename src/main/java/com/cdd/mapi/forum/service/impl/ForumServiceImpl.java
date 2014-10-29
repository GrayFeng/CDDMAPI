package com.cdd.mapi.forum.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.common.pojo.Page;
import com.cdd.mapi.forum.dao.IForumDao;
import com.cdd.mapi.forum.service.IForumService;
import com.cdd.mapi.pojo.ForumAnswer;
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
	
	public void addSubject(ForumSubject forumSubject){
		forumDao.addSubject(forumSubject);
	}

	@Override
	public void addAnswer(ForumAnswer answer) {
		forumDao.addAnswer(answer);
	}

	@Override
	public ForumSubjectVO getForumSubjectById(Integer id) {
		return forumDao.getForumSubjectById(id);
	}

	@Override
	public List<ForumAnswer> getAnswerListBySubjectId(Integer subjectId) {
		return forumDao.getAnswerListBySubjectId(subjectId);
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
			page.setSize(prizeCount);
			page.setNumber(params.getPageNum());
			params.setStartNum(page.getStartNum());
			params.setSize(page.getSize());
			List<Map<String,Object>> mapList = forumDao.getSubjectList(params);
			if(mapList != null && !mapList.isEmpty()){
				list = Lists.newArrayList();
				for(Map<String,Object> mapInfo : mapList){
					ForumSubjectVO subjectVO = new ForumSubjectVO();
					subjectVO.setId((Integer)mapInfo.get("id"));
					subjectVO.setTitle((String)mapInfo.get("title"));
					subjectVO.setTitle((String)mapInfo.get("content"));
					subjectVO.setLikeCount((Integer)mapInfo.get("likeCount"));
					subjectVO.setShareCount((Integer)mapInfo.get("shareCount"));
					subjectVO.setFavoriteCount((Integer)mapInfo.get("favoriteCount"));
					subjectVO.setMemberId((Integer)mapInfo.get("memberId"));
					subjectVO.setItemId((Integer)mapInfo.get("itemId"));
					subjectVO.setSubItemId((Integer)mapInfo.get("subItemId"));
					subjectVO.setAnonymous((Integer)mapInfo.get("anonymous"));
					subjectVO.setMemberPhoto((String)mapInfo.get("photo"));
					subjectVO.setCreateTime((String)mapInfo.get("createTime"));
					ForumAnswerVO answerVO = new ForumAnswerVO();
					answerVO.setId((Integer)mapInfo.get("answerId"));
					answerVO.setContent((String)mapInfo.get("answerContent"));
					answerVO.setMemberId((Integer)mapInfo.get("answerMemberId"));
					answerVO.setLikeCount((Integer)mapInfo.get("answerlikeCount"));
					answerVO.setCreateTime((String)mapInfo.get("answerCreateTime"));
					answerVO.setMemberName((String)mapInfo.get("answerMemberName"));
					answerVO.setAnonymous((Integer)mapInfo.get("answerAnonymous"));
					answerVO.setMemberPhoto((String)mapInfo.get("answerMemberPhoto"));
					subjectVO.setAnswerList(Lists.newArrayList(answerVO));
					list.add(subjectVO);
				}
			}
		}
		return list;
	}


}
