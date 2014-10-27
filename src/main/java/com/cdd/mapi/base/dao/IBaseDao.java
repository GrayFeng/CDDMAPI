package com.cdd.mapi.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.City;

/**
 * Description: BaseDao.java
 * All Rights Reserved.
 * @version 1.0  2014年10月27日 下午3:22:06  
 * @author Gray(jy.feng@zuche.com) 
 */

@Repository
@MyBatisRepository
public interface IBaseDao {
	
	public List<City> getCiytList();

}
