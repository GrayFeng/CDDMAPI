package com.cdd.mapi.pojo;

/**
 * Description: MemberLevel.java
 * All Rights Reserved.
 * @version 1.0  2014年10月28日 上午9:22:41  
 * @author Gray(jy.feng@zuche.com) 
 */

public class MemberLevel {
	
	private Integer id;
	
	private String name;
	
	private Long scoreRangeStart;
	
	private Long scoreRangeEnd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getScoreRangeStart() {
		return scoreRangeStart;
	}

	public void setScoreRangeStart(Long scoreRangeStart) {
		this.scoreRangeStart = scoreRangeStart;
	}

	public Long getScoreRangeEnd() {
		return scoreRangeEnd;
	}

	public void setScoreRangeEnd(Long scoreRangeEnd) {
		this.scoreRangeEnd = scoreRangeEnd;
	}
	
}
