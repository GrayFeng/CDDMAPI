package com.cdd.mapi.pojo;

import java.util.Date;

/**
 * CDDMAPI
 * @date 2014-11-2 下午9:32:13
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class COFAffiliatedInfo {
	
	private Integer id;
	
	private Integer memberId;
	
	private Integer cofId;
	
	private Integer type;
	
	private Date createTime;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public Integer getCofId() {
		return cofId;
	}

	public void setCofId(Integer cofId) {
		this.cofId = cofId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
