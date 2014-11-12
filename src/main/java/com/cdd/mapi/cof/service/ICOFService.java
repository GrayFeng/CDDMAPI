package com.cdd.mapi.cof.service;

import java.util.List;

import com.cdd.mapi.pojo.COFAffiliatedInfo;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.COFReplyVO;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsVO;
import com.cdd.mapi.pojo.ForwardNews;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:20:09
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface ICOFService {
	
	public void addNews(CircleOfFriends cir);
	
	public void addPhotos(List<String> photos, Integer memberId,
			Integer cofId);
	
	public void addReply(COFReply reply);
	
	public List<CofNewsVO> getMemberCofNewsList(Integer pageNum,Integer memberId);
	
	public List<CofNewsVO> getCofNewsList(Integer pageNum,Integer memberId);
	
	public List<CofNewsVO> getFavNewsList(Integer pageNum,Integer memberId);
	
	public List<CofNewsVO> getHotNewsList(Integer pageNum);
	
	public List<COFReplyVO> getReplyListByNewsId(Integer cofId,Integer pageNum);
	
	public void addCofAffiliated(COFAffiliatedInfo affiliatedInfo);
	
	public Integer findCofAffiliatedInfo(COFAffiliatedInfo affiliatedInfo);
	
	public void forwardNews(ForwardNews forwardNews);
	
	public CofNewsVO getNewsInfoById(Integer newsId);

}
