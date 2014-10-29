package com.cdd.mapi.pojo;

import java.util.List;

/**
 * Description: ForumSubjectVO.java
 * All Rights Reserved.
 * @version 1.0  2014年10月29日 下午2:07:18  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ForumSubjectVO {
	
	private Integer id;
	
	private String title;
	
	private String content;
	
	private Integer memberId;
	
	private String memberName;
	
	private String memberPhoto;
	
	private Integer likeCount;
	
	private Integer shareCount;
	
	private Integer favoriteCount;
	
	private Integer anonymous;
	
	private Integer itemId;
	
	private Integer subItemId;
	
	private String createTime;
	
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	private List<ForumAnswerVO> answerList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<ForumAnswerVO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<ForumAnswerVO> answerList) {
		this.answerList = answerList;
	}
	
}
