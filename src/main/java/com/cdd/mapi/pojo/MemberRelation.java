package com.cdd.mapi.pojo;

import java.util.Date;

/**
 * CDDMAPI
 * @date 2014-11-3 下午9:37:01
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class MemberRelation {
	
	private Integer id;
	
	private Integer fans;
	
	private Integer popular_person;
	
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	public Integer getPopular_person() {
		return popular_person;
	}

	public void setPopular_person(Integer popular_person) {
		this.popular_person = popular_person;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
