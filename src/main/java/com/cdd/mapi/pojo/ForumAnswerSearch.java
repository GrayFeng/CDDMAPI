package com.cdd.mapi.pojo;

/**
 * Description: ForumSubjectSearch.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 下午4:43:42  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ForumAnswerSearch {
	
	private Integer subjectId;
	
	private Integer startNum;
	
	private Integer size;
	
	private Integer pageNum;
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
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
