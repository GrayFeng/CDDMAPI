package com.cdd.mapi.schedule;

/**
 * Description: PushMessageJob.java
 * All Rights Reserved.
 * @version 1.0  2014年11月25日 上午11:13:30  
 * @author Gray(jy.feng@zuche.com)
 */

import com.cdd.mapi.base.service.IBaseService;
import com.cdd.mapi.common.SpringConstant;
import com.cdd.mapi.common.uitls.JPushUtils;
import com.cdd.mapi.pojo.PushMessage;
import com.google.common.collect.Lists;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class PushMessageJob extends QuartzJobBean {

    private static boolean working = false;

    private Logger log = LoggerFactory.getLogger(PushMessageJob.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        if (working) {
            log.info("--------------------消息推送任务正在运行中，本次任务注销----------------------");
            return;
        }
        log.info("--------------------消息推送任务启动----------------------");
        working = true;
        IBaseService baseService = (IBaseService) SpringConstant.getBean("baseServiceImpl");
        try {
            List<PushMessage> msgList = baseService.getPushMsgList();
            log.info(Thread.currentThread().getName() + "-本次共扫描到待推送消息：" + (CollectionUtils.isEmpty(msgList) ? "0" : Integer.valueOf(msgList.size())) + "条");
            if ((msgList != null) && (msgList.size() > 0)) {
                List<Integer> successList = Lists.newArrayList();
                List<Integer> failList = Lists.newArrayList();
                for (PushMessage pushMessage : msgList) {
                    if ((StringUtils.isNotEmpty(pushMessage.getDeviceNo())) && (StringUtils.isNotEmpty(pushMessage.getMsg()))) {
                        boolean result = JPushUtils.getInstance().sendMsg(pushMessage.getDeviceNo(), pushMessage.getMsg());
                        if (!result) {
                            result = JPushUtils.getInstance().sendMsg(pushMessage.getDeviceNo(), pushMessage.getMsg());
                        }
                        if (result) {
                            successList.add(pushMessage.getId());
                        } else {
                            failList.add(pushMessage.getId());
                        }
                    }
                }
                if (successList.size() > 0) {
                    log.info(new StringBuilder().append(Thread.currentThread().getName()) + "-本次消息推送成功：" + successList.size() + "条");
                    baseService.updatePushMsgSuccessStatus(successList);
                }
                if (failList.size() > 0) {
                    log.info(new StringBuilder().append(Thread.currentThread().getName()) + "-本次消息推送成功：" + failList.size() + "条");
                    baseService.updatePushMsgFailStatus(failList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            working = false;
        }
    }
}