package com.cdd.mapi.remind.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.enums.EEchoCode;
import com.cdd.mapi.common.enums.ENoticeType;
import com.cdd.mapi.common.enums.ERemindType;
import com.cdd.mapi.common.pojo.Result;
import com.cdd.mapi.common.uitls.ResultUtil;
import com.cdd.mapi.member.service.IMemberService;
import com.cdd.mapi.pojo.Exam;
import com.cdd.mapi.pojo.ExamRemind;
import com.cdd.mapi.pojo.LearningPlan;
import com.cdd.mapi.pojo.Member;
import com.cdd.mapi.pojo.SysNotice;
import com.cdd.mapi.remind.service.IRemindService;
import com.google.common.collect.Maps;
@Controller
@RequestMapping("/api/remind")
public class RemindControl {
	
	private Logger log = LoggerFactory.getLogger(RemindControl.class);
	
	@Autowired
	private IRemindService remindService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IBaseService baseService;
	
	@RequestMapping("addExamRemind")
	@ResponseBody
	public String addExam(String uid,String params){
		Result result = null;
		try{
			ExamRemind examRemind = JSON.parseObject(params, ExamRemind.class);
			Member member = memberService.getMemberByUID(uid);
			if(examRemind != null && member != null
					&& examRemind.getCityId() != null
					&& examRemind.getItemId() != null
					&& examRemind.getType() != null){
				Exam exam = remindService.getExamById(examRemind.getCityId(), examRemind.getItemId());
				if(exam != null){
					Integer remnindCount = remindService.isExistRemind(member.getId(), examRemind.getItemId(),examRemind.getType());
					if(remnindCount == null || remnindCount == 0){
						boolean canSave = true;
						examRemind.setTitle(exam.getName());
						String remindTime = exam.getExamTime();
						if(remindTime != null){
							if(!isEffectiveTime(remindTime)){
								canSave = false;
								result = new Result(EEchoCode.ERROR.getCode(),"很抱歉，该考试已经开始，无法制定提醒");
							}
						}
						if(canSave && remindTime != null){
							examRemind.setRemindTime(remindTime);
							examRemind.setStartTime(examRemind.getRemindTime());
							examRemind.setEndTime(examRemind.getRemindTime());
							examRemind.setMemberId(member.getId());
							examRemind.setDes(exam.getDes());
							examRemind.setType(ERemindType.EXAM.getCode());
							remindService.addExamRemind(examRemind);
							examRemind.setRemindTime(exam.getSignUpTime());
							examRemind.setStartTime(exam.getSignUpTime());
							examRemind.setEndTime(exam.getSignUpEndTime());
							examRemind.setMemberId(member.getId());
							examRemind.setDes(exam.getDes());
							examRemind.setType(ERemindType.SIGN_UP.getCode());
							remindService.addExamRemind(examRemind);
							result = Result.getSuccessResult();
						}
					}else{
						result = new Result(EEchoCode.ERROR.getCode(),"您已制定过了此考试的考试提醒");
					}
				}else{
					result = new Result(EEchoCode.ERROR.getCode(),"很抱歉，暂未开放添加此考试提醒");
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，添加失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	
	private boolean isEffectiveTime(String time){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date remindDate;
		try {
			remindDate = sf.parse(time);
			if(remindDate.before(new Date())){
				return false;
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return true;
	}

	@RequestMapping("addLearingPlan")
	@ResponseBody
	public String addLearingPlan(String uid,String params){
		Result result = null;
		try{
			LearningPlan learningPlan = JSON.parseObject(params, LearningPlan.class);
			Member member = memberService.getMemberByUID(uid);
			if(learningPlan != null && member != null
					&& StringUtils.isNotEmpty(learningPlan.getTitle())
					&& StringUtils.isNotEmpty(learningPlan.getStartTime())
					&& StringUtils.isNotEmpty(learningPlan.getEndTime())){
				boolean canSave = true;
				try{
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = sf.parse(learningPlan.getStartTime());
					Calendar c = Calendar.getInstance();
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);
					if(startDate.before(c.getTime())){
						canSave = false;
						result = new Result(EEchoCode.ERROR.getCode(),"开始时间有误，请选择正确时间");
					}
				}catch(Exception e){
					log.error(e.getMessage(),e);
					canSave = false;
					result = new Result(EEchoCode.ERROR.getCode(),"时间格式错误，请选择正确时间");
				}
				if(canSave){
					Integer planCount = remindService.isExistLearningPlan(member.getId(), learningPlan.getStartTime());
					if(planCount == null || planCount == 0){
						learningPlan.setMemberId(member.getId());
						remindService.addLearningPlan(learningPlan);
						result = Result.getSuccessResult();
					}else{
						result = new Result(EEchoCode.ERROR.getCode(),"这段时间内您已经设置了其他的学习计划");
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，添加失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("delRemind")
	@ResponseBody
	public String delRemind(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer remindId = jsonObject.getInteger("remindId");
			Member member = memberService.getMemberByUID(uid);
			if(remindId != null && member != null){
				remindService.delRemid(member.getId(), remindId);
				result = Result.getSuccessResult();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，删除失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("getRemindList")
	@ResponseBody
	public String getRemindList(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			if(member != null){
				List<ExamRemind> remindList = remindService.getRemindList(member.getId());
				Map<String,Object> resultMap = Maps.newHashMap();
				List<SysNotice> noticeList = baseService.getNoticeList(ENoticeType.REMIND.getCode());
				resultMap.put("notice", noticeList);
				resultMap.put("list", remindList);
				result = Result.getSuccessResult();
				result.setRe(resultMap);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，读取失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("getLearningPlanList")
	@ResponseBody
	public String getLearningPlanList(String uid,String params){
		Result result = null;
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer isExpert = jsonObject.getInteger("isExpert");
			String startTime = jsonObject.getString("startTime");
			String endTime = jsonObject.getString("endTime");
			Member member = memberService.getMemberByUID(uid);
			if(member != null 
					&& StringUtils.isNotEmpty(startTime)
					&& StringUtils.isNotEmpty(endTime)){
				List<LearningPlan> learningPlanList = null;
				if(isExpert != null && isExpert == 1){
					learningPlanList = remindService.getExpertLearningPlanList(startTime, endTime);
				}else{
					learningPlanList = remindService.getLearningPlanList(member.getId(),startTime, endTime);
				}
				result = Result.getSuccessResult();
				result.setRe(learningPlanList);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，读取失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("remindInfo")
	@ResponseBody
	public String remindInfo(String uid,String params){
		Result result = null;
		try{
			Member member = memberService.getMemberByUID(uid);
			JSONObject jsonObject = JSON.parseObject(params);
			Integer remindId = jsonObject.getInteger("remindId");
			if(member != null && remindId != null){
				ExamRemind remind = remindService.getRemindInfo(member.getId(), remindId);
				result = Result.getSuccessResult();
				result.setRe(remind);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"信息不全，读取失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
}
