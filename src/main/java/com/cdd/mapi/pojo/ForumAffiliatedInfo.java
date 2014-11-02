package com.cdd.mapi.pojo;

import java.util.Date;

/**
 * CDDMAPI
 * @date 2014-11-2 下午9:32:13
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class ForumAffiliatedInfo {
	
	private Integer id;
	
	private Integer memberId;
	
	private Integer questionId;
	
	private Integer answerId;
	
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

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
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
