package net.netne.test.dao;

import java.util.List;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.City;

import org.springframework.stereotype.Repository;

/**
 * Description: ITestDao.java
 * All Rights Reserved.
 * @version 1.0  2014年7月25日 下午1:54:28  
 * @author Gray(jy.feng@zuche.com) 
 */

@Repository
@MyBatisRepository
public interface ITestDao {
	
	public List<City> getCityList();

}
