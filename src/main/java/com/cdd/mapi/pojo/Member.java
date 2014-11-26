package com.cdd.mapi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * diceGame
 * @date 2014-8-11 下午9:46:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Member implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String name;
	
	private String loginId;
	
	private String password;
	
	private String confirmPassword;
	
	private String mobile;
	
	private Integer sex;
	
	private Integer levelId;
	
	private String photo;
	
	private String email;
	
	private String description;
	//积分上限值
	private Long scoreCeiling;
	//可用积分
	private Long availableScore;
	//设备标识
	private String deviceFlag;
	//注册来源
	private Integer origin;
	//是否为管理员
	private Integer isAdmin;
	//签到日期
	private Date signTime;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private Integer status;
	
	private Integer cityId;
	
	//关系，1-互相已关注；0-我的粉丝/我的关注
	private Integer relation;
	
	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public String getDeviceFlag() {
		return deviceFlag;
	}

	public void setDeviceFlag(String deviceFlag) {
		this.deviceFlag = deviceFlag;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public Long getScoreCeiling() {
        return scoreCeiling;
    }

    public void setScoreCeiling(Long scoreCeiling) {
        this.scoreCeiling = scoreCeiling;
    }

    public Long getAvailableScore() {
        return availableScore;
    }

    public void setAvailableScore(Long availableScore) {
        this.availableScore = availableScore;
    }
}
