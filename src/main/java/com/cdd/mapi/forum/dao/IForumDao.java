package com.cdd.mapi.forum.dao;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.ForumSubject;

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

}
