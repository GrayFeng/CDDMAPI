package com.cdd.mapi.pojo;

/**
 * Description: ForumSubjectSearch.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 下午4:43:42  
 * @author Gray(jy.feng@zuche.com) 
 */

public class CofNewsSearch {
	
	private Integer startNum;
	
	private Integer size;
	
	private Integer pageNum;
	
	private Integer memberId;
	
	private String keyWord;
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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
