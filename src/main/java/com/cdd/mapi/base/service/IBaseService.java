package com.cdd.mapi.base.service;

import java.util.List;

import com.cdd.mapi.pojo.City;
import com.cdd.mapi.pojo.MemberLevel;
import com.cdd.mapi.pojo.Province;

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

}
