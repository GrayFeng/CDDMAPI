package com.cdd.mapi.forum.service;

import java.util.List;

import com.cdd.mapi.pojo.ForumAnswer;
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
	
	public List<ForumAnswer> getAnswerListBySubjectId(Integer subjectId);
	
	public Integer getSubjectCount(ForumSubjectSearch params);
	
	public List<ForumSubjectVO> getSubjectList(ForumSubjectSearch params);

}
