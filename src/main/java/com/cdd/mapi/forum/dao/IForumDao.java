package com.cdd.mapi.forum.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.ForumAffiliatedInfo;
import com.cdd.mapi.pojo.ForumAnswer;
import com.cdd.mapi.pojo.ForumAnswerSearch;
import com.cdd.mapi.pojo.ForumAnswerVO;
import com.cdd.mapi.pojo.ForumSubject;
import com.cdd.mapi.pojo.ForumSubjectSearch;
import com.cdd.mapi.pojo.ForumSubjectVO;

/**
 * CDDMAPI
 * @date 2014-10-28 下午9:40:42
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Repository
@MyBatisRepository
public interface IForumDao {
	
	public void addSubject(ForumSubject forumSubject);
	
	public void addAnswer(ForumAnswer answer);
	
	public ForumSubjectVO getForumSubjectById(Integer id);
	
	public List<ForumAnswerVO> getAnswerListBySubjectId(ForumAnswerSearch params);
	
	public Integer getSubjectCount(ForumSubjectSearch params);
	
	public List<Map<String,Object>> getSubjectList(ForumSubjectSearch params);
	
	public Integer getHotSubjectCount(ForumSubjectSearch params);
	
	public List<Map<String,Object>> getHotSubjectList(ForumSubjectSearch params);
	
	public Integer getAnswerCountBySubjectId(Integer subjectId);
	
	public void addPhotos(Map<String,Object> params);
	
	public void updateSubjectLikeCount(Integer subjectId);
	
	public void updateSubjectFavCount(Integer subjectId);
	
	public void updateSubjectShareCount(Integer subjectId);
	
	public void updateAnswerLikeCount(Integer answerId);
	
	public void addForumAffiliated(ForumAffiliatedInfo forumAffiliatedInfo);

}
