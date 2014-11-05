package com.cdd.mapi.pojo;
/**
 * CDDMAPI
 * @date 2014-11-6 上午12:12:31
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class PrivateLetter {
	
	private Integer id;
	
	private Integer from;
	
	private Integer to;
	
	private String msg;
	
	private String creatTime;
	
	private Integer status;
	
	private Integer isRead;
	
	private Integer replyId;
	
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

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
}
