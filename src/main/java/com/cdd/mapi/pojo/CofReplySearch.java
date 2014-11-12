package com.cdd.mapi.pojo;

/**
 * Description: ForumSubjectSearch.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 下午4:43:42  
 * @author Gray(jy.feng@zuche.com) 
 */

public class CofReplySearch {
	
	private Integer startNum;
	
	private Integer size;
	
	private Integer pageNum;
	
	private Integer cofId;
	
	public Integer getCofId() {
		return cofId;
	}

	public void setCofId(Integer cofId) {
		this.cofId = cofId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
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
