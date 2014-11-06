package com.cdd.mapi.cof.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsSearch;
import com.cdd.mapi.pojo.CofNewsVO;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:09:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Repository
@MyBatisRepository
public interface ICOFDao {
	
	public void addNews(CircleOfFriends cir);
	
	public void addPhotos(Map<String,Object> params);
	
	public void addReply(COFReply reply);
	
	public List<CofNewsVO> getNewsList(CofNewsSearch search);
	
	public Integer getNewsCount(CofNewsSearch search);

}
