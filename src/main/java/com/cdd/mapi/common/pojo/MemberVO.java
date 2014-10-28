package com.cdd.mapi.common.pojo;


/**
 * Description: MemberVO.java
 * All Rights Reserved.
 * @version 1.0  2014年10月28日 下午2:43:01  
 * @author Gray(jy.feng@zuche.com) 
 */

public class MemberVO {
	
private Integer id;
	
	private String name;
	
	private String mobile;
	
	private Integer sex;
	
	private Integer levelId;
	
	private String photo;
	
	private String email;
	
	private String description;
	//积分上限值
	private String scoreCeiling;
	//可用积分
	private String availableScore;
	
	private Integer status;
	
	private Integer cityId;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScoreCeiling() {
		return scoreCeiling;
	}

	public void setScoreCeiling(String scoreCeiling) {
		this.scoreCeiling = scoreCeiling;
	}

	public String getAvailableScore() {
		return availableScore;
	}

	public void setAvailableScore(String availableScore) {
		this.availableScore = availableScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
}
