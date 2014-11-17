package com.cdd.mapi.cof.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.mapi.cof.dao.ICOFDao;
import com.cdd.mapi.cof.service.ICOFService;
import com.cdd.mapi.common.enums.EAffiliatedType;
import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.common.pojo.Page;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.COFAffiliatedInfo;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.COFReplyVO;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsSearch;
import com.cdd.mapi.pojo.CofNewsVO;
import com.cdd.mapi.pojo.CofReplySearch;
import com.cdd.mapi.pojo.ForumSubjectVO;
import com.cdd.mapi.pojo.ForwardNews;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:21:01
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class COFServiceImpl implements ICOFService{
	
	private Logger log = LoggerFactory.getLogger(COFServiceImpl.class);
	
	@Autowired
	private ICOFDao cofDao;
	@Autowired
	private IMemberService memberService;
	
	private final LoadingCache<String, List<?>> searchDataCache = CacheBuilder
			.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES)
			.maximumSize(600).build(new CacheLoader<String, List<?>>() {
				@Override
				public List<?> load(String key) throws Exception {
					return searchNewsFromCache(key);
				}
		});

	@Override
	@Transactional
	public void addNews(CircleOfFriends cof) {
		cofDao.addNews(cof);
		addPhotos(cof.getPhotos(), cof.getMemberId(), cof.getId());
		memberService.addMemberScore(cof.getMemberId(), EScoreRuleType.ADD_NEWS);
	}

	@Override
	public void addPhotos(List<String> photos, Integer memberId, Integer cofId) {
		if(photos != null && !photos.isEmpty() 
				&& (memberId != null || cofId != null)){
			Map<String,Object> map = Maps.newHashMap();
			map.put("memberId", memberId);
			map.put("cofId",cofId);
			map.put("photos",photos);
			cofDao.addPhotos(map);
		}
	}

	@Override
	@Transactional
	public void addReply(COFReply reply) {
		cofDao.addReply(reply);
		memberService.addMemberScore(reply.getMemberId(), EScoreRuleType.REPLY_NEWS);
	}
	
	@Override
	public List<CofNewsVO> getCofNewsList(Integer pageNum,Integer memberId) {
		List<CofNewsVO> list = null;
		CofNewsSearch cofNewsSearch = new CofNewsSearch();
		cofNewsSearch.setMemberId(memberId);
		Integer prizeCount = cofDao.getNewsCount(cofNewsSearch);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			if(page.getTotalPages() >= pageNum){
				cofNewsSearch.setStartNum(page.getStartNum());
				cofNewsSearch.setSize(page.getSize());
				list = cofDao.getNewsList(cofNewsSearch);
			}
		}
		return list;
	}

	@Override
	public List<CofNewsVO> getHotNewsList(Integer pageNum) {
		List<CofNewsVO> list = null;
		CofNewsSearch cofNewsSearch = new CofNewsSearch();
		Integer prizeCount = cofDao.getHotNewsCount(cofNewsSearch);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			if(page.getTotalPages() >= pageNum){
				cofNewsSearch.setStartNum(page.getStartNum());
				cofNewsSearch.setSize(page.getSize());
				list = cofDao.getHotNewsList(cofNewsSearch);
			}
		}
		return list;
	}

	@Override
	public List<COFReplyVO> getReplyListByNewsId(Integer cofId, Integer pageNum) {
		List<COFReplyVO> list = null;
		CofReplySearch cofReplySearch = new CofReplySearch();
		cofReplySearch.setCofId(cofId);
		Integer prizeCount = cofDao.getReplyCountByNewsId(cofReplySearch);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			if(page.getTotalPages() >= pageNum){
				cofReplySearch.setStartNum(page.getStartNum());
				cofReplySearch.setSize(page.getSize());
				list = cofDao.getReplyListByNewsId(cofReplySearch);
			}
		}
		return list;
	}

	@Override
	public List<CofNewsVO> getMemberCofNewsList(Integer pageNum,
			Integer memberId) {
		List<CofNewsVO> list = null;
		CofNewsSearch cofNewsSearch = new CofNewsSearch();
		cofNewsSearch.setMemberId(memberId);
		Integer prizeCount = cofDao.getMemberNewsCount(cofNewsSearch);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			if(page.getTotalPages() >= pageNum){
				cofNewsSearch.setStartNum(page.getStartNum());
				cofNewsSearch.setSize(page.getSize());
				list = cofDao.getMemberNewsList(cofNewsSearch);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public void addCofAffiliated(COFAffiliatedInfo affiliatedInfo) {
		if(affiliatedInfo.getType() != null){
			if(affiliatedInfo.getCofId() != null){
				cofDao.addCofAffiliated(affiliatedInfo);
				if(EAffiliatedType.LIKE.getCode().equals(affiliatedInfo.getType())){
					cofDao.updateCofLikeCount(affiliatedInfo.getCofId());
					memberService.addMemberScore(affiliatedInfo.getMemberId(), EScoreRuleType.LIKE_NEWS);
				}else if(EAffiliatedType.FAV.getCode().equals(affiliatedInfo.getType())){
					cofDao.updateCofFavCount(affiliatedInfo.getCofId());
				}else if(EAffiliatedType.SHARE.getCode().equals(affiliatedInfo.getType())){
					ForwardNews forwardNews = new ForwardNews();
					forwardNews.setMemberId(affiliatedInfo.getMemberId());
					forwardNews.setNewsId(affiliatedInfo.getCofId());
					CofNewsVO cofNewsVO = getNewsInfoById(affiliatedInfo.getCofId());
					if(cofNewsVO != null 
							&& Integer.valueOf(1).equals(cofNewsVO.getIsForward()) 
							&& cofNewsVO.getForwardCofId() != null){
						forwardNews.setNewsId(cofNewsVO.getForwardCofId());
					}
					forwardNews(forwardNews);
					cofDao.updateCofShareCount(affiliatedInfo.getCofId());
					memberService.addMemberScore(affiliatedInfo.getMemberId(), EScoreRuleType.SHARE_NEWS);
				}
			}
		}
	}

	@Override
	public Integer findCofAffiliatedInfo(COFAffiliatedInfo affiliatedInfo) {
		return cofDao.findCofAffiliatedInfo(affiliatedInfo);
	}

	@Override
	public void forwardNews(ForwardNews forwardNews) {
		cofDao.forwardNews(forwardNews);
	}

	@Override
	public CofNewsVO getNewsInfoById(Integer newsId) {
		return cofDao.getNewsInfoById(newsId);
	}

	@Override
	public List<CofNewsVO> getFavNewsList(Integer pageNum, Integer memberId) {
		List<CofNewsVO> list = null;
		CofNewsSearch cofNewsSearch = new CofNewsSearch();
		cofNewsSearch.setMemberId(memberId);
		Integer prizeCount = cofDao.getFavNewsCount(cofNewsSearch);
		if(prizeCount != null && prizeCount > 0){
			Page page = new Page();
			page.setTotal(prizeCount);
			page.setSize(20);
			pageNum = pageNum == null ? 1 : pageNum;
			page.setNumber(pageNum);
			if(page.getTotalPages() >= pageNum){
				cofNewsSearch.setStartNum(page.getStartNum());
				cofNewsSearch.setSize(page.getSize());
				list = cofDao.getFavNewsList(cofNewsSearch);
			}
		}
		return list;
	}

	@Override
	public List<CofNewsVO> searchNews(Integer pageNum, String keyWord) {
		List<CofNewsVO> list = null;
		try{
			List<CofNewsVO> allList = (List<CofNewsVO>)searchDataCache.get(keyWord);
			if(allList != null){
				Integer prizeCount = allList.size();
				if(prizeCount != null && prizeCount > 0){
					Page page = new Page();
					page.setTotal(prizeCount);
					page.setSize(20);
					pageNum = pageNum == null ? 1 : pageNum;
					page.setNumber(pageNum);
					if(page.getTotalPages() >= pageNum){
						list = Lists.newArrayList();
						for(int i = 0;i < page.getSize() ; i++){
							int index = page.getStartNum() + i;
							if(index < allList.size()){
								list.add(allList.get(index));
							}else{
								break;
							}
						}
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return list;
	}
	
	private List<CofNewsVO> searchNewsFromCache(String keyWord){
		CofNewsSearch cofNewsSearch = new CofNewsSearch();
		cofNewsSearch.setKeyWord(keyWord);
		return cofDao.searchNews(cofNewsSearch);
	}
}
