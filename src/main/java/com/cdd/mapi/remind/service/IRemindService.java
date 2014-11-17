package com.cdd.mapi.remind.service;

import java.util.List;

import com.cdd.mapi.pojo.Exam;
import com.cdd.mapi.pojo.ExamRemind;
import com.cdd.mapi.pojo.LearningPlan;

/**
 * CDDMAPI
 * @date 2014-11-16 下午8:44:32
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IRemindService {
	
	public Exam getExamById(Integer cityId,Integer itemId);
	
	public void addExamRemind(ExamRemind examRemind);
	
	public void addLearningPlan(LearningPlan plan);
	
	public void delRemid(Integer memberId,Integer remindId);
	
	public Integer isExistLearningPlan(Integer memberId,String startTime);
	
	public Integer isExistRemind(Integer memberId, Integer itemId,Integer type);
	
	public List<ExamRemind> getRemindList(Integer memberId);
	
	public List<LearningPlan> getLearningPlanList(Integer memberId,String startTime,String endTime);
	
	public List<LearningPlan> getExpertLearningPlanList(String startTime,String endTime);
	
	public ExamRemind getRemindInfo(Integer memberId,Integer id);

}
