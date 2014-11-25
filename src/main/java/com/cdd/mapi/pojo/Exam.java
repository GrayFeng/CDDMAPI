package com.cdd.mapi.pojo;
/**
 * CDDMAPI
 * @date 2014-11-16 下午7:34:06
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Exam {
	
	private Integer id;
	
	private Integer cityId;
	
	private Integer itemId;
	
	private String name;
	
	private String signUpTime;
	
	private String signUpEndTime;
	
	private String examTime;
	
	private String des;
	
	private Integer status;
	
	private String createTime;
	
	public String getSignUpEndTime() {
		return signUpEndTime;
	}

	public void setSignUpEndTime(String signUpEndTime) {
		this.signUpEndTime = signUpEndTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignUpTime() {
		return signUpTime;
	}

	public void setSignUpTime(String signUpTime) {
		this.signUpTime = signUpTime;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
