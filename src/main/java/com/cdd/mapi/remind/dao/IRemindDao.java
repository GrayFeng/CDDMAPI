package com.cdd.mapi.remind.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cdd.mapi.common.annotation.MyBatisRepository;
import com.cdd.mapi.pojo.Exam;
import com.cdd.mapi.pojo.ExamRemind;
import com.cdd.mapi.pojo.LearningPlan;

/**
 * CDDMAPI
 * @date 2014-11-16 下午7:48:56
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@MyBatisRepository
@Repository
public interface IRemindDao {
	
	public Exam getExamById(Map<String,Object> paramsMap);
	
	public void addExamRemind(ExamRemind examRemind);
	
	public void addLearningPlan(LearningPlan plan);
	
	public void delRemid(Map<String,Object> paramsMap);
	
	public Integer isExistLearningPlan(Map<String,Object> paramsMap);
	
	public Integer isExistRemind(Map<String,Object> paramsMap);
	
	public List<ExamRemind> getRemindList(Integer memberId);
	
	public List<LearningPlan> getLearningPlanList(Map<String,Object> paramsMap);
	
	public List<LearningPlan> getExpertLearningPlanList(Map<String,Object> paramsMap);
	
	public ExamRemind getRemindInfo(Map<String,Object> paramsMap);
	
}
