package com.cdd.mapi.cof.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.mapi.cof.dao.ICOFDao;
import com.cdd.mapi.cof.service.ICOFService;
import com.cdd.mapi.common.pojo.Page;
import com.cdd.mapi.pojo.COFReply;
import com.cdd.mapi.pojo.CircleOfFriends;
import com.cdd.mapi.pojo.CofNewsSearch;
import com.cdd.mapi.pojo.CofNewsVO;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:21:01
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class COFServiceImpl implements ICOFService{
	
	@Autowired
	private ICOFDao cofDao;

	@Override
	@Transactional
	public void addNews(CircleOfFriends cof) {
		cofDao.addNews(cof);
		addPhotos(cof.getPhotos(), cof.getMemberId(), cof.getId());
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
	public void addReply(COFReply reply) {
		cofDao.addReply(reply);
	}
	
	@Override
	public List<CofNewsVO> getCofNewsLisst(Integer pageNum,Integer memberId) {
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

}
