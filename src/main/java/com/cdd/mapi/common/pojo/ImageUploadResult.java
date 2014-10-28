package com.cdd.mapi.common.pojo;

public class ImageUploadResult{
	
	boolean success;
	
	String msg;
	
	String url;
	
	public ImageUploadResult(){}
	
	public ImageUploadResult(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
