package com.cdd.mapi.remind.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdd.mapi.pojo.Exam;
import com.cdd.mapi.pojo.ExamRemind;
import com.cdd.mapi.pojo.LearningPlan;
import com.cdd.mapi.remind.dao.IRemindDao;
import com.cdd.mapi.remind.service.IRemindService;
import com.google.common.collect.Maps;

/**
 * CDDMAPI
 * @date 2014-11-16 下午8:45:51
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class RemindServiceImpl implements IRemindService{
	
	@Autowired
	private IRemindDao remindDao;

	@Override
	public Exam getExamById(Integer cityId, Integer itemId) {
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("cityId", cityId);
		paramsMap.put("itemId", itemId);
		return remindDao.getExamById(paramsMap);
	}

	@Override
	public void addExamRemind(ExamRemind examRemind) {
		remindDao.addExamRemind(examRemind);
	}

	@Override
	public void addLearningPlan(LearningPlan plan) {
		remindDao.addLearningPlan(plan);
	}

	@Override
	public void delRemid(Integer memberId, Integer remindId) {
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("memberId", memberId);
		paramsMap.put("remindId", remindId);
		remindDao.delRemid(paramsMap);
	}

	@Override
	public Integer isExistLearningPlan(Integer memberId, String startTime) {
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("memberId", memberId);
		paramsMap.put("startTime", startTime);
		return remindDao.isExistLearningPlan(paramsMap);
	}

	@Override
	public Integer isExistRemind(Integer memberId, Integer itemId,Integer type) {
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("memberId", memberId);
		paramsMap.put("itemId", itemId);
		paramsMap.put("type", type);
		return remindDao.isExistRemind(paramsMap);
	}

	@Override
	public List<ExamRemind> getRemindList(Integer memberId) {
		return remindDao.getRemindList(memberId);
	}

	@Override
	public List<LearningPlan> getLearningPlanList(Integer memberId,
			String startTime, String endTime) {
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("memberId", memberId);
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return remindDao.getLearningPlanList(paramsMap);
	}

	@Override
	public List<LearningPlan> getExpertLearningPlanList(String startTime,
			String endTime) {
		Map<String,Object> paramsMap = Maps.newHashMap();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		return remindDao.getExpertLearningPlanList(paramsMap);
	}

}
