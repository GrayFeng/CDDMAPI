package com.cdd.mapi.member.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.mapi.common.Constant;
import com.cdd.mapi.common.annotation.NotNeedLogin;
import com.cdd.mapi.common.cache.MemberCache;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.pojo.LoginInfo;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.Member;

/**
 * CDDMAPI
 * @date 2014-10-27 下午8:09:26
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api/member")
public class MemberController {
	
	private Logger log = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("reg")
	@NotNeedLogin
	@ResponseBody
	public String reg(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isHave(uid)){
				Member member = JSON.parseObject(params, Member.class);
				if(member != null && StringUtils.isNotEmpty(member.getName())
						&& StringUtils.isNotEmpty(member.getLoginId())
						&& StringUtils.isNotEmpty(member.getPassword())
						&& StringUtils.isNotEmpty(member.getConfirmPassword())){
					result = executeReg(member,uid);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"用户信息不完整，注册失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	private Result executeReg(Member member,String uid){
		if(!member.getPassword().equals(member.getConfirmPassword())){
			return new Result(EEchoCode.ERROR.getCode(),"密码输入不一致!");
		}
		if(memberService.getMemberByLoginId(member.getLoginId()) != null){
			return new Result(EEchoCode.ERROR.getCode(),"账号已被注册!");
		}
		if(memberService.getMemberByName(member.getName()) != null){
			return new Result(EEchoCode.ERROR.getCode(),"昵称已存在!");
		}
		memberService.addMember(member);
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setMember(member);
		MemberCache.getInstance().add(uid, loginInfo);
		return Result.getSuccessResult();
	}
	
	@RequestMapping("login")
	@ResponseBody
	@NotNeedLogin
	public  String login(String uid,String params,HttpServletRequest request){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(params)){
				JSONObject paramsObj = JSON.parseObject(params);
				if(StringUtils.isNotEmpty(uid) 
						&& MemberCache.getInstance().isHave(uid)){
					String loginId = paramsObj.getString("loginId");
					String password = paramsObj.getString("password");
					if(StringUtils.isNotEmpty(loginId) 
							&& StringUtils.isNotEmpty(password)){
						Member member = memberService.login(loginId,password,Constant.getClientIp(request));
						if(member != null){
							LoginInfo loginInfo = new LoginInfo();
							loginInfo.setMember(member);
							MemberCache.getInstance().add(uid, loginInfo);
							result = Result.getSuccessResult();
							result.setRe(member);
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"账号号或密码错误!");
			}
		}
		return ResultUtil.getJsonString(result);
	}

}
