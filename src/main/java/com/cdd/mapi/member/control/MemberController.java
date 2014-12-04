package com.cdd.mapi.member.control;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.enums.EMemberOrigin;
import com.cdd.mapi.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.mapi.common.Constant;
import com.cdd.mapi.common.annotation.NotNeedLogin;
import com.cdd.mapi.common.cache.MemberCache;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.enums.EUploadType;
import com.cdd.mapi.common.pojo.ImageUploadResult;
import com.cdd.mapi.common.pojo.LoginInfo;
import com.cdd.mapi.common.pojo.MemberVO;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.serivce.IUploadService;
import com.cdd.mapi.common.uitls.CommonValidate;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.member.service.IMemberService;
import com.google.common.collect.Maps;

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
	@Autowired
	private IUploadService uploadService;
    @Autowired
    private IBaseService baseService;
	
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
                    member.setOrigin(EMemberOrigin.APP.getCode());
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
		if(!CommonValidate.checkMobile(member.getLoginId()) 
				&& !CommonValidate.checkEmail(member.getLoginId())){
			return new Result(EEchoCode.ERROR.getCode(),"请输入正确的手机号或邮箱地址");
		}
		if(!member.getPassword().equals(member.getConfirmPassword())){
			return new Result(EEchoCode.ERROR.getCode(),"密码输入不一致");
		}
		if(memberService.getMemberByLoginId(member.getLoginId()) != null){
			return new Result(EEchoCode.ERROR.getCode(),"账号已被注册");
		}
		if(memberService.getMemberByName(member.getName()) != null){
			return new Result(EEchoCode.ERROR.getCode(),"昵称已存在");
		}
		if(CommonValidate.checkMobile(member.getLoginId())){
			member.setMobile(member.getLoginId());
		}else if(CommonValidate.checkEmail(member.getLoginId())){
			member.setEmail(member.getLoginId());
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
					String deviceFlag = paramsObj.getString("deviceFlag");
					if(StringUtils.isNotEmpty(loginId) 
							&& StringUtils.isNotEmpty(password)){
						Member member = memberService.login(loginId,password,Constant.getClientIp(request));
						if(member != null){
							if(StringUtils.isNotEmpty(deviceFlag) 
									&& !deviceFlag.equals(member.getDeviceFlag())){
								member.setDeviceFlag(deviceFlag);
								memberService.updateMemberDeviceFlag(member);
							}
							LoginInfo loginInfo = new LoginInfo();
							loginInfo.setMember(member);
							MemberCache.getInstance().add(uid, loginInfo);
							result = Result.getSuccessResult();
							MemberVO memberVO = memberService.transformMember(member);
							result.setRe(memberVO);
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"账号或密码错误!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("loginByQQ")
	@ResponseBody
	@NotNeedLogin
	public  String loginByQQ(String uid,String params,HttpServletRequest request){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(params)){
				JSONObject paramsObj = JSON.parseObject(params);
				if(StringUtils.isNotEmpty(uid) 
						&& MemberCache.getInstance().isHave(uid)){
					String qq = paramsObj.getString("qq");
					String deviceFlag = paramsObj.getString("deviceFlag");
					if(StringUtils.isNotEmpty(qq) && NumberUtils.isNumber(qq)){
						Member member = memberService.getMemberByLoginId(qq);
						if(member == null){
							member = new Member();
							member.setLoginId(qq);
							member.setPassword(String.valueOf(new Random().nextInt(99999999)));
							member.setName(qq);
                            member.setOrigin(EMemberOrigin.QQ.getCode());
							memberService.addMember(member);
							member = memberService.getMemberByLoginId(qq);
						}
						if(member != null){
							if(StringUtils.isNotEmpty(deviceFlag) 
									&& !deviceFlag.equals(member.getDeviceFlag())){
								member.setDeviceFlag(deviceFlag);
								memberService.updateMemberDeviceFlag(member);
							}
							LoginInfo loginInfo = new LoginInfo();
							loginInfo.setMember(member);
							MemberCache.getInstance().add(uid, loginInfo);
							result = Result.getSuccessResult();
							MemberVO memberVO = memberService.transformMember(member);
							result.setRe(memberVO);
						}
					}else{
						result = new Result(EEchoCode.ERROR.getCode(),"请输入正确的qq号码");
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"授权异常，登录失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("logout")
	@ResponseBody
	public String logout(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isHave(uid)){
				MemberCache.getInstance().remove(uid);
				result = Result.getSuccessResult();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"注销失败，缺少认证信息!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("updateMember")
	@ResponseBody
	public String updateMember(String uid,String params){
		Result result = null;
		try{
			Member member = JSON.parseObject(params, Member.class);
			if(member != null){
				Member tempMember = memberService.getMemberByUID(uid);
				JSONObject jsonObject = JSON.parseObject(params);
				String defaultPhoto = jsonObject.getString("defaultPhoto");
				if(tempMember != null){
					if(StringUtils.isNotEmpty(member.getName())){
						if(memberService.getMemberByName(member.getName()) != null){
							result = new Result(EEchoCode.ERROR.getCode(),"昵称已存在");
						}
						tempMember.setName(member.getName());
					}
					if(member.getCityId() != null){
						tempMember.setCityId(member.getCityId());
					}
					if(StringUtils.isNotEmpty(member.getName())){
						tempMember.setName(member.getName());
					}
					if(StringUtils.isNotEmpty(member.getDescription())){
						tempMember.setDescription(member.getDescription());
					}
					if(member.getSex() != null){
						tempMember.setSex(member.getSex());
					}
					if("1".equals(defaultPhoto)){
						tempMember.setPhoto(null);
					}
					if(result == null){
						memberService.updateMember(tempMember,uid);
						result = Result.getSuccessResult();
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"没有需要修改的信息");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("modifyPassword")
	@ResponseBody
	public String modifyPassword(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(params)){
				JSONObject paramsObj = JSON.parseObject(params);
				String password = paramsObj.getString("password");
				String confirmPassword = paramsObj.getString("confirmPassword");
				String oldPassword = paramsObj.getString("oldPassword");
				if(StringUtils.isNotEmpty(password) 
						&& StringUtils.isNotEmpty(confirmPassword)){
					Member member = memberService.getMemberByUID(uid);
					if(member != null && password.equals(confirmPassword)){
						if(member.getPassword().equals(oldPassword)){
							member.setPassword(password);
							memberService.updateMember(member,uid);
							result = Result.getSuccessResult();
						}else{
							result = new Result(EEchoCode.ERROR.getCode(),"旧密码不正确");
						}
					}else{
						result = new Result(EEchoCode.ERROR.getCode(),"两次密码不一致");
					}
				}else{
					result = new Result(EEchoCode.ERROR.getCode(),"请输入需要修改的密码");
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"密码修改失败");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	/**
	 * 只支持上传一个文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadMemberPhoto", method = RequestMethod.POST)
	@ResponseBody
	public String uploadMemberPhoto(String uid,MultipartFile file) {
		Result result = null;
		Member member = memberService.getMemberByUID(uid);
		if(member != null){
			ImageUploadResult imageUploadResult =  uploadService.processupload(member.getId(),file,EUploadType.MEMBER_PHOTO);
			if(imageUploadResult.isSuccess() 
					&& StringUtils.isNotEmpty(imageUploadResult.getUrl())){
				memberService.deleteMemberPhoto(member.getId());
				member.setPhoto(Constant.PHOTO_URL_PATH +"/"+ imageUploadResult.getUrl());
				memberService.updateMember(member,uid);
				Map<String,String> paramMap = Maps.newHashMap();
				paramMap.put("url",member.getPhoto());
				result = new Result(EEchoCode.SUCCESS.getCode(),"上传成功");
				result.setRe(paramMap);
			}else{
				result = new Result(EEchoCode.ERROR.getCode(), imageUploadResult.getMsg());
			}
		}else{
			result = new Result(EEchoCode.ERROR.getCode(), "信息不全，上传失败");
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("memberInfo")
	@ResponseBody
	public String memberInfo(String uid,String params){
		Result result = null;
		try{
			Integer memberId =null;
			if(StringUtils.isNotEmpty(params)){
				JSONObject jsonObject = JSON.parseObject(params);
				memberId = jsonObject.getInteger("memberId");
			}
			Member member = memberService.getMemberByUID(uid);
			if(memberId == null && member != null){
				memberId = member.getId();
			}
			result = Result.getSuccessResult();
			Integer relation = memberService.getMemberRelation(member.getId(), memberId);
			member = memberService.getMemberById(memberId);
			MemberVO memberVO = memberService.transformMember(member);
			memberVO.setRelation(relation);
			result.setRe(memberVO);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取用户信息失败");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("signIn")
	@ResponseBody
	public String signIn(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			if(member != null){
				Date lastSignInTime = member.getSignTime();
				if(lastSignInTime == null){
					member.setSignTime(new Date());
					memberService.signIn(member);
					result = Result.getSuccessResult();
				}else{
					if(Constant.isSignIn(lastSignInTime) == 0){
						member.setSignTime(new Date());
						Integer score = memberService.signIn(member);
						if(score != null){
							result = Result.getSuccessResult();
							Map<String,Object> map = Maps.newHashMap();
							map.put("score", score);
							result.setRe(map);
						}
					}else{
						result = new Result(EEchoCode.ERROR.getCode(), "今日已签到过了哦");
					}
				}
				LoginInfo loginInfo = MemberCache.getInstance().get(uid);
				if(loginInfo != null){
					loginInfo.setMember(member);
					MemberCache.getInstance().add(uid, loginInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常，签到失败");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("attention")
	@ResponseBody
	public String attention(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer idolId = jsonObject.getInteger("idolId");
			if(member != null && idolId != null){
				if(idolId.equals(member.getId())){
					MemberRelation memberRelation = new MemberRelation();
					memberRelation.setFans(member.getId());
					memberRelation.setPopular_person(idolId);
					memberService.attention(memberRelation);
					result = Result.getSuccessResult();
				}else{
					result = new Result(EEchoCode.ERROR.getCode(),"不能关注自己");
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"关注失败，缺少参数信息");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("unAttention")
	@ResponseBody
	public String unAttention(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer idolId = jsonObject.getInteger("idolId");
			if(member != null && idolId != null){
				MemberRelation memberRelation = new MemberRelation();
				memberRelation.setFans(member.getId());
				memberRelation.setPopular_person(idolId);
				memberService.unAttention(memberRelation);
				result = Result.getSuccessResult();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"取消关注失败，缺少参数信息");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("getIdolList")
	@ResponseBody
	public String getIdolList(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			Integer pageNum = null;
			Integer memberId =null;
			if(StringUtils.isNotEmpty(params)){
				JSONObject jsonObject = JSON.parseObject(params);
				memberId = jsonObject.getInteger("memberId");
				pageNum = jsonObject.getInteger("pageNum");
			}
			List<MemberVO> list = null;
			if(member != null){
				if(memberId == null){
					memberId = member.getId();
					list = memberService.getIdolList(memberId, pageNum);
				}else{
					list = memberService.getOtherMemberIdolList(memberId, member.getId(), pageNum);
				}
			}
			result = Result.getSuccessResult();
			result.setRe(list);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取关注列表失败，缺少参数信息");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("getFansList")
	@ResponseBody
	public String getFansList(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			Integer pageNum = null;
			Integer memberId =null;
			if(StringUtils.isNotEmpty(params)){
				JSONObject jsonObject = JSON.parseObject(params);
				memberId = jsonObject.getInteger("memberId");
				pageNum = jsonObject.getInteger("pageNum");
			}
			List<MemberVO> list = null;
			if(member != null){
				if(memberId == null){
					memberId = member.getId();
					list = memberService.getFansList(memberId, pageNum);
				}else{
					list = memberService.getOtherMemberFansList(memberId, member.getId(), pageNum);
				}
			}
			result = Result.getSuccessResult();
			result.setRe(list);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"读取粉丝列表失败，缺少参数信息");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("sendMsg")
	@ResponseBody
	public String sendPrivateMessage(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			PrivateLetter letter = JSON.parseObject(params, PrivateLetter.class);
			if(member != null && letter != null){
				if(letter.getTo() != null 
						&& StringUtils.isNotEmpty(letter.getMsg())){
					letter.setFrom(member.getId());
					memberService.sendPrivateMessage(letter);
					result = Result.getSuccessResult();
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，发送失败");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("letterList")
	@ResponseBody
	public String getPrivateLetterList(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			JSONObject jsonObject = JSON.parseObject(params);
			Integer pageNum = null;
			if(jsonObject != null){
				pageNum = jsonObject.getInteger("pageNum");
			}
			List<PrivateLetterVO> list = null;
			if(member != null){
				list = memberService.getPrivateLetterList(member.getId(), pageNum);
			}
			Map<String,Object> map = Maps.newHashMap();
			if(list != null){
				map.put("size", list.size());
				map.put("letterList", list);
			}else{
				map.put("size", 0);
				map.put("letterList", null);
			}
			result = Result.getSuccessResult();
			result.setRe(map);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常，读取私信列表失败");
			}
		}
		return ResultUtil.getJsonString(result);
	}

    @RequestMapping("share")
    @ResponseBody
    public String share(String params){
        Result result = null;
        Map<String,Object> resultMap = Maps.newHashMap();
        if(StringUtils.isNotEmpty(params)){
            JSONObject jsonObject = JSON.parseObject(params);
            String cid = jsonObject.getString("cid");
            VersionInfo versionInfo = baseService.checkVersion(cid);
            result = Result.getSuccessResult();
            resultMap.put("picUrl",Constant.PHOTO_URL_PATH + "/logo.png");
            resultMap.put("msg", "财务社区-财叮铛");
            if(versionInfo == null){
                resultMap.put("url", "http://a.app.qq.com/");
            }else{
                if(versionInfo.getChannel().equals("100")){
                    resultMap.put("url", "http://a.app.qq.com/");
                }else if(versionInfo.getChannel().equals("200")){
                    resultMap.put("url", versionInfo.getAddress());
                }else{
                    resultMap.put("url", versionInfo.getAddress());
                }
            }
            result.setRe(resultMap);
        }
        return ResultUtil.getJsonString(result);
    }
}
