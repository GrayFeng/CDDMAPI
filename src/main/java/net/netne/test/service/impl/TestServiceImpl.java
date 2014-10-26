package net.netne.test.service.impl;

import java.util.List;

import net.netne.common.pojo.City;
import net.netne.test.dao.ITestDao;
import net.netne.test.service.ITestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: TestServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年7月25日 下午1:36:39  
 * @author Gray(jy.feng@zuche.com) 
 */

@Service
public class TestServiceImpl implements ITestService{
	
	@Autowired
	private ITestDao testDao;

	@Override
	public void test() {
		List<City> cityList = testDao.getCityList();
		if(cityList != null){
			for(City city : cityList){
				System.out.println(city.getName());
			}
		}
	}

}
