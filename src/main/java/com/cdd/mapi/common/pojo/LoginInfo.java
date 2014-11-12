package com.cdd.mapi.common.pojo;

import java.io.Serializable;
import java.util.Date;

import com.cdd.mapi.pojo.Member;

public class LoginInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Date lastAccessTime;
	
	private Member member;

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public LoginInfo(){
		this.lastAccessTime = new Date();
	}
	
}
