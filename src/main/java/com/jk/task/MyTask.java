package com.jk.task;

import com.jk.job.HelloJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by dell on 2017/5/29.
 */
@Service
public class MyTask {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct //等同于init-method
    public void init(){
        //定义jobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job1","group1").build();

        //定义trigger
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1").startNow().withSchedule(simpleScheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
