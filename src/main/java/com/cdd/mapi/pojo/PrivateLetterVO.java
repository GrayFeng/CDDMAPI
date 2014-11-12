package com.cdd.mapi.pojo;
/**
 * CDDMAPI
 * @date 2014-11-6 上午12:12:31
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class PrivateLetterVO {
	
	private Integer id;
	
	private Integer fromMemberId;
	
	private Integer toMemberId;
	
	private String msg;
	
	private String createTime;
	
	private Integer isRead;
	
	private Integer replyId;
	
	private String fromMemberName;
	
	private String fromMemberPhoto;
	
	private String fromMemberSex;
	
	public String getFromMemberSex() {
		return fromMemberSex;
	}

	public void setFromMemberSex(String fromMemberSex) {
		this.fromMemberSex = fromMemberSex;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFromMemberName() {
		return fromMemberName;
	}

	public void setFromMemberName(String fromMemberName) {
		this.fromMemberName = fromMemberName;
	}

	public String getFromMemberPhoto() {
		return fromMemberPhoto;
	}

	public void setFromMemberPhoto(String fromMemberPhoto) {
		this.fromMemberPhoto = fromMemberPhoto;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromMemberId() {
		return fromMemberId;
	}

	public void setFromMemberId(Integer fromMemberId) {
		this.fromMemberId = fromMemberId;
	}

	public Integer getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(Integer toMemberId) {
		this.toMemberId = toMemberId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreatTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
}
