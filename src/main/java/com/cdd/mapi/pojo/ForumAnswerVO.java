package com.cdd.mapi.pojo;

import java.util.List;

/**
 * Description: ForumAnswerVO.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 下午2:16:33  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ForumAnswerVO {
	
	private Integer id;
	
	private String content;
	
	private Integer memberId;
	
	private String memberName;
	
	private Integer memberSex;
	
	private String memberLevelName;
	
	private String memberPhoto;
	
	private Integer likeCount;
	
	private Integer subjectId;
	
	private Integer anonymous;
	
	private List<ForumPhoto> photos;
	
	private String createTime;
	
	public Integer getMemberSex() {
		return memberSex;
	}

	public void setMemberSex(Integer memberSex) {
		this.memberSex = memberSex;
	}

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}

	public List<ForumPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<ForumPhoto> photos) {
		this.photos = photos;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
	
}
