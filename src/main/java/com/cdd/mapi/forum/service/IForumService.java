package com.cdd.mapi.forum.service;

import java.util.List;
import java.util.Map;

import com.cdd.mapi.pojo.ForumAffiliatedInfo;
import com.cdd.mapi.pojo.ForumAnswer;
import com.cdd.mapi.pojo.ForumAnswerVO;
import com.cdd.mapi.pojo.ForumSubject;
import com.cdd.mapi.pojo.ForumSubjectSearch;
import com.cdd.mapi.pojo.ForumSubjectVO;

/**
 * CDDMAPI
 * @date 2014-10-28 下午9:40:56
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IForumService {
	
	public void addSubject(ForumSubject forumSubject);
	
	public void addAnswer(ForumAnswer answer);
	
	public ForumSubjectVO getForumSubjectById(Integer id);
	
	public List<ForumAnswerVO> getAnswerListBySubjectId(Integer subjectId,Integer pageNum);
	
	public List<ForumSubjectVO> getHotSubjectList(ForumSubjectSearch params);
	
	public List<ForumSubjectVO> getMySubjectList(ForumSubjectSearch params);
	
	public List<ForumSubjectVO> getFavSubjectList(ForumSubjectSearch params);
	
	public List<ForumSubjectVO> getSubjectList(ForumSubjectSearch params);
	
	public Integer getAnswerCountBySubjectId(Integer subjectId);
	
	public void addPhotos(List<String> photos,Integer subjectId,Integer answerId);
	
	public void addForumAffiliated(ForumAffiliatedInfo forumAffiliatedInfo);
	
	public Integer findForumAffiliatedInfo(ForumAffiliatedInfo forumAffiliatedInfo);
	
	public List<ForumSubjectVO> searchSubject(ForumSubjectSearch params);

}
