package com.cdd.mapi.pojo;

/**
 * Description: ForwardNews.java
 * All Rights Reserved.
 * @version 1.0  2014年11月12日 下午3:58:39  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ForwardNewsVO {
	
	private Integer id;
	private String content;
	private Integer memberId;
	private String  createTime;
	private String  memberName;
	private Integer  memberSex;
	private String memberPhoto;
	private String photo;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getMemberSex() {
		return memberSex;
	}
	public void setMemberSex(Integer memberSex) {
		this.memberSex = memberSex;
	}
	public String getMemberPhoto() {
		return memberPhoto;
	}
	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
