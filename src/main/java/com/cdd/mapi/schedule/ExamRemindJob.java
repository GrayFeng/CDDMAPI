package com.cdd.mapi.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.SpringConstant;
import com.cdd.mapi.common.enums.ERemindType;
import com.cdd.mapi.pojo.PushMessage;
import com.cdd.mapi.pojo.Remind4Push;
import com.cdd.mapi.remind.service.IRemindService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * Description: ExamRemindJob.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午11:16:38  
 * @author Gray(jy.feng@zuche.com) 
 */

public class ExamRemindJob extends QuartzJobBean
{
  private Logger log = LoggerFactory.getLogger(ExamRemindJob.class);

  protected void executeInternal(JobExecutionContext context) throws JobExecutionException{
    log.info("---------------------考试提醒分析任务启动-------------------");
    IRemindService remindService = (IRemindService)SpringConstant.getBean("remindServiceImpl");
    IBaseService baseService = (IBaseService)SpringConstant.getBean("baseServiceImpl");
    try {
      Calendar c = Calendar.getInstance();
      String startTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
      c.add(5, 1);
      String planEndTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
      c.add(5, 6);
      String endTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
      Integer remindCount = remindService.getRemindPageCount4Push(startTime, endTime, ERemindType.EXAM.getCode());
      if (remindCount.intValue() > 0) {
        List<Remind4Push> remindList = null;
        for (int i = 1; i <= remindCount.intValue(); i++) {
          remindList = remindService.getRemindList4Push(startTime, endTime, ERemindType.EXAM.getCode(), Integer.valueOf(i));
          if (remindList != null) {
            List<PushMessage> pushMessages = transform(remindList, ERemindType.EXAM);
            baseService.addPushMsg(pushMessages);
          }
        }
      }

      remindCount = remindService.getRemindPageCount4Push(startTime, endTime, ERemindType.SIGN_UP.getCode());
      if (remindCount.intValue() > 0) {
        List<Remind4Push> remindList = null;
        for (int i = 1; i <= remindCount.intValue(); i++) {
          remindList = remindService.getRemindList4Push(startTime, endTime, ERemindType.SIGN_UP.getCode(), Integer.valueOf(i));
          if (remindList != null) {
        	List<PushMessage> pushMessages = transform(remindList, ERemindType.SIGN_UP);
            baseService.addPushMsg(pushMessages);
          }
        }
      }

      remindCount = remindService.getRemindPageCount4Push(startTime, planEndTime, ERemindType.LEARING.getCode());
      if (remindCount.intValue() > 0) {
        List<Remind4Push> remindList = null;
        for (int i = 1; i <= remindCount.intValue(); i++) {
          remindList = remindService.getRemindList4Push(startTime, planEndTime, ERemindType.LEARING.getCode(), Integer.valueOf(i));
          if (remindList != null) {
        	List<PushMessage> pushMessages = transform(remindList, ERemindType.LEARING);
            baseService.addPushMsg(pushMessages);
          }
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private List<PushMessage> transform(List<Remind4Push> remindList,final ERemindType type) {
    Collection<PushMessage> pushMessages = Collections2.transform(remindList,new Function<Remind4Push,PushMessage>() {
	@Override
	public PushMessage apply(Remind4Push input) {
		PushMessage message = new PushMessage();
        message.setMemberId(input.getMemberId());
        message.setDeviceNo(input.getDeviceNo());
        String title = input.getTitle();
        Long diffDay = ExamRemindJob.this.getDiffDay(input.getRemindTime());
        switch (type) {
        case EXAM:
          if ((diffDay != null) && (diffDay.longValue() > 0L))
            title = "距离" + title + "还有" + diffDay + "天";
          else {
            title = title + "开始了！";
          }
          break;
        case SIGN_UP:
          if ((diffDay != null) && (diffDay.longValue() > 0L))
            title = "距离开始" + title + "还有" + diffDay + "天";
          else {
            title = "今天要开始" + title + "喽！";
          }
          break;
        case LEARING:
          if ((diffDay != null) && (diffDay.longValue() > 0L))
            title = "距离" + title + "还有" + diffDay + "天";
          else {
            title = title + "开始了！";
          }
          break;
        }
        message.setMsg(title);
        return message;
	}
    });
    return Lists.newArrayList(pushMessages);
  }

  private Long getDiffDay(String time) {
    if (StringUtils.isNotEmpty(time)) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        Date remindTime = sf.parse(time);
        Calendar c = Calendar.getInstance();
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        long diff = remindTime.getTime() - c.getTimeInMillis();
        long dayMillis = 86400000L;
        return Long.valueOf(diff / dayMillis);
      } catch (ParseException e) {
        log.error(e.getMessage());
      }
    }
    return null;
  }
}
