package com.cdd.mapi.cof.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.COFAffiliatedInfo;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.COFReplyVO;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsSearch;
import com.cdd.mapi.pojo.CofNewsVO;
import com.cdd.mapi.pojo.CofReplySearch;
import com.cdd.mapi.pojo.ForwardNews;

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
	
	public Integer getMemberNewsCount(CofNewsSearch search);
	
	public List<CofNewsVO> getMemberNewsList(CofNewsSearch search);
	
	public Integer getNewsCount(CofNewsSearch search);
	
	public List<CofNewsVO> getNewsList(CofNewsSearch search);
	
	public Integer getHotNewsCount(CofNewsSearch search);
	
	public List<CofNewsVO> getHotNewsList(CofNewsSearch search);
	
	public Integer getReplyCountByNewsId(CofReplySearch search);
	
	public List<COFReplyVO> getReplyListByNewsId(CofReplySearch search);
	
	public Integer getFavNewsCount(CofNewsSearch search);
	
	public List<CofNewsVO> getFavNewsList(CofNewsSearch search);
	
	public void updateCofLikeCount(Integer subjectId);
	
	public void updateCofFavCount(Integer subjectId);
	
	public void updateCofShareCount(Integer subjectId);
	
	public void addCofAffiliated(COFAffiliatedInfo affiliatedInfo);
	
	public Integer findCofAffiliatedInfo(COFAffiliatedInfo affiliatedInfo);
	
	public void forwardNews(ForwardNews forwardNews);
	
	public CofNewsVO getNewsInfoById(Integer newsId);
	
	public List<CofNewsVO> searchNews(CofNewsSearch search);

}
