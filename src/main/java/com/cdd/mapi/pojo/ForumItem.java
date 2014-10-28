package com.cdd.mapi.pojo;

import java.util.List;

/**
 * CDDMAPI
 * @date 2014-10-28 下午8:54:23
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class ForumItem {
	
	private Integer id;
	
	private String name;
	
	private List<ExamItem> subItems;

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

	public List<ExamItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<ExamItem> subItems) {
		this.subItems = subItems;
	}
}
