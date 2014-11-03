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
	
	private String levelName;
	
	private String photo;
	
	private String email;
	
	private String description;
	//可用积分
	private String availableScore;
	
	private Integer status;
	
	private Integer cityId;
	
	private String cityName;
	//今日是否签到过
	private Integer isSignIn;
	//粉丝数量
	private Long fansCount = 0L;
	//动态信息数量
	private Long dynamicInfoCount = 0L;
	//关注的人数量
	private Long idolCount = 0L;
	
	public Long getFansCount() {
		return fansCount;
	}

	public void setFansCount(Long fansCount) {
		this.fansCount = fansCount;
	}

	public Long getDynamicInfoCount() {
		return dynamicInfoCount;
	}

	public void setDynamicInfoCount(Long dynamicInfoCount) {
		this.dynamicInfoCount = dynamicInfoCount;
	}

	public Long getIdolCount() {
		return idolCount;
	}

	public void setIdolCount(Long idolCount) {
		this.idolCount = idolCount;
	}

	public Integer getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(Integer isSignIn) {
		this.isSignIn = isSignIn;
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

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
