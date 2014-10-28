package com.cdd.mapi.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.forum.dao.IForumDao;
import com.cdd.mapi.forum.service.IForumService;
import com.cdd.mapi.pojo.ForumSubject;

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

}
