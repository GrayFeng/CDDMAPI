package com.cdd.mapi.pojo;

/**
 * Description: Remind4Push.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午11:12:20  
 * @author Gray(jy.feng@zuche.com) 
 */

public class Remind4Push {

	 private Integer memberId;
	 
	 private Integer type;
	 
	 private String title;
	 
	 private String deviceNo;
	 
	 private String remindTime;
	 
	 private String startTime;
	 
	 private String endTime;
	 
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
}
