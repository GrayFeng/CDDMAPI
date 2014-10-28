package com.cdd.mapi.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.base.dao.IBaseDao;
import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.pojo.City;
import com.cdd.mapi.pojo.MemberLevel;
import com.cdd.mapi.pojo.Province;

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

}
