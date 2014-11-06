package com.cdd.mapi.pojo;

import java.util.List;

/**
 * CDDMAPI
 * @date 2014-11-6 下午11:06:23
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CofNewsVO {
	
	private Integer id;
	
	private String content;
	
	private Integer memberId;
	
	private String memberName;
	
	private Integer memberSex;
	
	private String memberLevelName;
	
	private Integer status;
	
	private Integer isHot;
	
	private Integer likeCount;
	
	private Integer shareCount;
	
	private Integer favoriteCount;
	
	private List<ForumPhoto> photos;
	
	private String createTime;
	
	private Integer type;
	
	private List<COFReplyVO> replyList;
	
	public List<COFReplyVO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<COFReplyVO> replyList) {
		this.replyList = replyList;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
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

	public List<ForumPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<ForumPhoto> photos) {
		this.photos = photos;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
}
