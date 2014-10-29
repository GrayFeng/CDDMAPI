package com.cdd.mapi.pojo;

import java.util.Date;

/**
 * Description: ForumAnswer.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 上午9:43:40  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ForumAnswer {
	
	private Integer id;
	
	private String content;
	
	private Integer memberId;
	
	private Integer subjectId;
	
	private Integer likeCount;
	
	private Integer status;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private Integer anonymous;
	
	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
