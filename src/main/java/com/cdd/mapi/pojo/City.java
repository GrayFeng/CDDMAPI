package com.cdd.mapi.pojo;

/**
 * Description: City.java
 * All Rights Reserved.
 * @version 1.0  2014年7月25日 下午2:15:59  
 * @author Gray(jy.feng@zuche.com) 
 */

public class City {

	private	Integer id;
	
	private String name;
	
	private String enName;
	
	private Integer provinceId;
	
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
