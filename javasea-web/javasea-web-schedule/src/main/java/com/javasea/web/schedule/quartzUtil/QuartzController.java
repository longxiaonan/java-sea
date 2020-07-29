package com.javasea.web.schedule.quartzUtil;

import org.quartz.SchedulerException;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName QuartzController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/7/5 0005 16:10
 */
@RestController
@RequestMapping("/schedule")
public class QuartzController {


    /**
     * @Description jobName
     * @Author longxiaonan@163.com
     * @Date 17:32 2020/7/5 0005
     **/
    @GetMapping("/jobs")
    public List<JobVo> listJob() throws SchedulerException {
        List<CronTriggerImpl> jobList = QuartzMgr.getInstance().getJobList();
        List<JobVo> jobVoList = new ArrayList<>();
        jobList.forEach(cronTrigger -> {
            String jobName = cronTrigger.getJobKey().getName();
            Date startTime = cronTrigger.getStartTime();
            Date previousFireTime = cronTrigger.getPreviousFireTime();
            Date nextFireTime = cronTrigger.getNextFireTime();
            String cronExpression = cronTrigger.getCronExpression();
            JobVo jobVo = new JobVo();
            jobVo.setJobName(jobName);
            jobVo.setStartTime(startTime);
            jobVo.setPreviousFireTime(previousFireTime);
            jobVo.setNextFireTime(nextFireTime);
            jobVo.setCronExpression(cronExpression);
        });
        return jobVoList;
    }

    /**
     * 添加cron job
     * @param jobVo * * * * * ?
     * @throws SchedulerException
     */
    @PostMapping("/job")
    public void addJob(@RequestBody JobVo jobVo) throws SchedulerException {
        QuartzMgr.getInstance().addCronJob(jobVo.getJobName(), QuartzMgr.TestJob.class, jobVo.getCronExpression());
        QuartzMgr.getInstance().getJobList();
    }
    /**
     * 修改cron job
     * @param jobName b
     * @param cronExpres 0/3 * * * * ?
     */
    @PutMapping("/job/{jobName}/{cronExpres}")
    public void update(@PathVariable String jobName, @PathVariable String cronExpres) throws ParseException, SchedulerException {
        //修改cron任务
        QuartzMgr.getInstance().modifyJobTime(jobName, cronExpres);
    }

    /**
     * 删除job
     * @param jobName b
     */
    @DeleteMapping("/job/{jobName}")
    public void delete(@PathVariable String jobName) throws SchedulerException {
        //修改cron任务
        QuartzMgr.getInstance().removeJob(jobName);
    }
}
