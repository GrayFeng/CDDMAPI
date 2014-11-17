package com.cdd.mapi.base.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.cdd.mapi.pojo.SysNotice;
import com.cdd.mapi.pojo.VersionInfo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;

/**
 * Description: BaseServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年10月27日 下午3:36:43  
 * @author Gray(jy.feng@zuche.com) 
 */

@Service
public class BaseServiceImpl implements IBaseService{
	
	private Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	private IBaseDao baseDao;
	
	private enum ListCacheKey {
		PROVINCE,CITY,MEMBERLEVEL,SCORERULE,FORUMITEM,EXAMITEM
	}
	
	private final LoadingCache<ListCacheKey, List<?>> dataCache = CacheBuilder
			.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES)
			.maximumSize(600).build(new CacheLoader<ListCacheKey, List<?>>() {
				@Override
				public List<?> load(ListCacheKey key) throws Exception {
					List<?> result = null;
					switch (key) {
					case PROVINCE:
						result = baseDao.getProvinceList();
						break;
					case CITY:
						result = baseDao.getCiytList();
						break;
					case MEMBERLEVEL:
						result = baseDao.getMemberLevelList();
						break;
					case SCORERULE:
						result = baseDao.getScoreRuleList();
						break;
					case FORUMITEM:
						result = baseDao.getForumItemList();
						break;
					case EXAMITEM:
						result = baseDao.getExamItemList();
						break;
					default:
						break;
					}
					return result;
				}
			});
	
	@Override
	public List<City> getCityList() {
		List<City> result = null;
		try {
			result = (List<City>)dataCache.get(ListCacheKey.CITY);
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);;
		}
		return result;
	}

	@Override
	public List<Province> getProvinceList() {
		List<Province> result = null;
		try {
			result = (List<Province>)dataCache.get(ListCacheKey.PROVINCE);
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);;
		}
		return result;
	}

	@Override
	public List<MemberLevel> getMemberLevelList() {
		List<MemberLevel> result = null;
		try {
			result = (List<MemberLevel>)dataCache.get(ListCacheKey.MEMBERLEVEL);
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);;
		}
		return result;
	}

	@Override
	public Map<Integer,ScoreRule> getScoreRuleMap() {
		Map<Integer,ScoreRule> map = Maps.newHashMap();
		List<ScoreRule> list = null;
		try {
			list = (List<ScoreRule>)dataCache.get(ListCacheKey.SCORERULE);
			for(ScoreRule scoreRule : list){
				map.put(scoreRule.getType(), scoreRule);
			}
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);;
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
		List<ForumItem> forumItems = null;
		try {
			forumItems = (List<ForumItem>)dataCache.get(ListCacheKey.FORUMITEM);
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);;
		}
		if(forumItems != null){
			for(ForumItem forumItem : forumItems){
				if(forumItem.getId() == 9){
					forumItem.setSubItems(getExamItemList());
				}
			}
		}
		return forumItems;
	}

	@Override
	public List<ExamItem> getExamItemList() {
		List<ExamItem> result = null;
		try {
			result = (List<ExamItem>)dataCache.get(ListCacheKey.EXAMITEM);
		} catch (ExecutionException e) {
			log.error(e.getMessage(),e);;
		}
		return result;
	}

	@Override
	public List<SysNotice> getNoticeList(Integer type) {
		return baseDao.getNoticeList(type);
	}
	
	@Override
	public VersionInfo checkVersion(String verStr) {
		List<VersionInfo> versionList = baseDao.getVersionList();

        if (null == verStr || verStr.length() < 4) {
            return null;
        }

        String channel = verStr.substring(0, 3);
        String verNum = verStr.substring(3);

        for (VersionInfo versionInfo : versionList) {
            if (versionInfo.getChannel().equalsIgnoreCase(channel)) {
            	versionInfo.setUpgrade(versionInfo.getNewver() > Ints.tryParse(verNum));
                return versionInfo;
            }
        }
		return null;
	}
}
