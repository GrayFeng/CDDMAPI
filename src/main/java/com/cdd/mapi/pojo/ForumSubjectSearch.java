package com.cdd.mapi.pojo;

/**
 * Description: ForumSubjectSearch.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 下午4:43:42  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ForumSubjectSearch {
	
	private Integer itemId;
	
	private Integer subItemId;
	
	private Integer isHot;
	
	private Integer startNum;
	
	private Integer size;
	
	private Integer pageNum;
	
	private Integer memberId;
	
	private Integer answerMemberId;
	
	public Integer getAnswerMemberId() {
		return answerMemberId;
	}

	public void setAnswerMemberId(Integer answerMemberId) {
		this.answerMemberId = answerMemberId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getSubItemId() {
		return subItemId;
	}

	public void setSubItemId(Integer subItemId) {
		this.subItemId = subItemId;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
