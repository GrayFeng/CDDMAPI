package com.cdd.mapi.pojo;
/**
 * CDDMAPI
 * @date 2014-11-6 下午11:33:48
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class COFReply {
	
	private Integer id;
	
	private Integer memberId;
	
	private Integer cofId;
	
	private String message;
	
	private String createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getCofId() {
		return cofId;
	}

	public void setCofId(Integer cofId) {
		this.cofId = cofId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
