package com.cdd.mapi.base.service;

import java.util.List;
import java.util.Map;

import com.cdd.mapi.common.enums.EScoreRuleType;
import com.cdd.mapi.pojo.City;
import com.cdd.mapi.pojo.ExamItem;
import com.cdd.mapi.pojo.ForumItem;
import com.cdd.mapi.pojo.MemberLevel;
import com.cdd.mapi.pojo.Province;
import com.cdd.mapi.pojo.ScoreRule;
import com.cdd.mapi.pojo.SysNotice;
import com.cdd.mapi.pojo.VersionInfo;

/**
 * Description: IBaseDao.java
 * All Rights Reserved.
 * @version 1.0  2014年10月27日 下午3:36:27  
 * @author Gray(jy.feng@zuche.com) 
 */

public interface IBaseService {
	
	public List<City> getCityList();
	
	public List<Province> getProvinceList();
	
	public List<MemberLevel> getMemberLevelList();
	
	public Map<Integer,ScoreRule> getScoreRuleMap();
	
	public int getScoreByType(EScoreRuleType type);
	
	public List<ForumItem> getForumItemList();
	
	public List<ExamItem> getExamItemList();
	
	public List<SysNotice> getNoticeList(Integer type);
	
	public VersionInfo checkVersion(String ver);

}
