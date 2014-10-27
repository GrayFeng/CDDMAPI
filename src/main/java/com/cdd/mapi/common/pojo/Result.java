package com.cdd.mapi.common.pojo;

import com.cdd.mapi.common.enums.EEchoCode;

/**
 * diceGame
 * @date 2014-8-11 下午10:01:11
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Result {
	
	public String status;
	
	public String msg;
	
	public Object re;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getRe() {
		return re;
	}

	public void setRe(Object re) {
		this.re = re;
	}
	
	public Result(){};
	
	public Result(String status,String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public static Result getSuccessResult(){
		return new Result(EEchoCode.SUCCESS.getCode(),"成功");
	}
}
