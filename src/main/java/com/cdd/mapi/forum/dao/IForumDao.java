package com.cdd.mapi.forum.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.ForumAnswer;
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
	
	public List<ForumAnswer> getAnswerListBySubjectId(Integer subjectId);
	
	public Integer getSubjectCount(ForumSubjectSearch params);
	
	public List<Map<String,Object>> getSubjectList(ForumSubjectSearch params);

}
