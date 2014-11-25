package com.cdd.mapi.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.google.common.collect.Lists;

/**
 * Description: ExamRemindJob.java
 * All Rights Reserved.
 *
 * @author Gray(jy.feng@zuche.com)
 * @version 1.0  2014年11月25日 上午11:16:38
 */

public class ExamRemindJob extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(ExamRemindJob.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("---------------------考试提醒分析任务启动-------------------");
        IRemindService remindService = (IRemindService) SpringConstant.getBean("remindServiceImpl");
        IBaseService baseService = (IBaseService) SpringConstant.getBean("baseServiceImpl");
        try {
            Calendar c = Calendar.getInstance();
            String startTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
            c.add(5, 1);
            String planEndTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
            c.add(5, 6);
            String endTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
            Integer remindCount = remindService.getRemindPageCount4Push(startTime, endTime, ERemindType.EXAM.getCode());
//      if (remindCount.intValue() > 0) {
//        List<Remind4Push> remindList = null;
//        for (int i = 1; i <= remindCount.intValue(); i++) {
//          remindList = remindService.getRemindList4Push(startTime, endTime, ERemindType.EXAM.getCode(), Integer.valueOf(i));
//          if (remindList != null) {
//            List<PushMessage> pushMessages = transform(remindList, ERemindType.EXAM);
//            baseService.addPushMsg(pushMessages);
//          }
//        }
//      }

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

    private List<PushMessage> transform(List<Remind4Push> remindList, ERemindType type) {
        List<PushMessage> list = Lists.newArrayList();
        for (Remind4Push remind4Push : remindList) {
            PushMessage message = new PushMessage();
            message.setMemberId(remind4Push.getMemberId());
            message.setDeviceNo(remind4Push.getDeviceNo());
            String msg = null;
            Long diffDay = getDiffDay(remind4Push.getStartTime());
            switch (type) {
                case SIGN_UP:
                    if (diffDay == 1 || diffDay == 3 || diffDay == 7) {
                        msg = "距离" + remind4Push.getTitle() + "报名开始还有" + diffDay + "天";
                    } else {
                        Long endTimeDiffDay = getDiffDay(remind4Push.getEndTime());
                        if (endTimeDiffDay != null && endTimeDiffDay == 1) {
                            msg = "距离" + remind4Push.getTitle() + "报名结束还有" + endTimeDiffDay + "天";
                        }
                    }
                    break;
                case LEARING:
                    if ((diffDay != null) && (diffDay.longValue() > 0L)) {
                        msg = "距离" + remind4Push.getTitle() + "还有" + diffDay + "天";
                    } else {
                        msg = remind4Push.getTitle() + "开始了！";
                    }
                    break;
                default:
                    break;
            }
            if (msg != null) {
                message.setMsg(msg);
                list.add(message);
            }
        }
        return list;
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
