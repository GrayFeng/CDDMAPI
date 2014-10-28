package com.cdd.mapi.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.base.dao.IBaseDao;
import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.pojo.City;
import com.cdd.mapi.pojo.ExamItem;
import com.cdd.mapi.pojo.ForumItem;
import com.cdd.mapi.pojo.MemberLevel;
import com.cdd.mapi.pojo.Province;
import com.cdd.mapi.pojo.ScoreRule;
import com.google.common.collect.Maps;

/**
 * Description: BaseServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年10月27日 下午3:36:43  
 * @author Gray(jy.feng@zuche.com) 
 */

@Service
public class BaseServiceImpl implements IBaseService{

	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public List<City> getCityList() {
		return baseDao.getCiytList();
	}

	@Override
	public List<Province> getProvinceList() {
		return baseDao.getProvinceList();
	}

	@Override
	public List<MemberLevel> getMemberLevelList() {
		return baseDao.getMemberLevelList();
	}

	@Override
	public Map<Integer,ScoreRule> getScoreRuleMap() {
		Map<Integer,ScoreRule> map = Maps.newHashMap();
		List<ScoreRule> list = baseDao.getScoreRuleList();
		for(ScoreRule scoreRule : list){
			map.put(scoreRule.getType(), scoreRule);
		}
		return map;
	}

	@Override
	public int getScoreByType(EScoreRuleType type) {
		Map<Integer,ScoreRule> map = getScoreRuleMap();
		if(map != null){
			ScoreRule scoreRule = map.get(type.getCode());
			if(scoreRule != null && scoreRule.getScore() != null){
				return scoreRule.getScore();
			}
		}
		return 0;
	}

	@Override
	public List<ForumItem> getForumItemList() {
		List<ForumItem> forumItems = baseDao.getForumItemList();
		if(forumItems != null){
			for(ForumItem forumItem : forumItems){
				if(forumItem.getId() == 9){
					forumItem.setSubItems(baseDao.getExamItemList());
				}
			}
		}
		return forumItems;
	}

	@Override
	public List<ExamItem> getExamItemList() {
		return baseDao.getExamItemList();
	}

}
