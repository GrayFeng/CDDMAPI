package com.cdd.mapi.pojo;

/**
 * Description: PushMessage.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午11:11:22  
 * @author Gray(jy.feng@zuche.com) 
 */

public class PushMessage {

	private Integer id;
	
	private String msg;
	
	private String deviceNo;
	
	private String createTime;
	
	private Integer status;
	
	private Integer memberId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
}
