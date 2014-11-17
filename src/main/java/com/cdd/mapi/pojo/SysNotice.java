package com.cdd.mapi.pojo;

/**
 * Description: SysNotice.java
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 上午9:21:24  
 * @author Gray(jy.feng@zuche.com) 
 */

public class SysNotice {
	
	private Integer id;
	
	private String message;
	
	private Integer type;
	
	private String createTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
