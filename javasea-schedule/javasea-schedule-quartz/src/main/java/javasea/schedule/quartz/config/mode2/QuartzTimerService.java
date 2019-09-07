package javasea.schedule.quartz.config.mode2;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author ：F嘉阳
 * @date ：2019/7/29 8:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTimerService {

    @Autowired
    private Scheduler scheduler;

    public void buildGoodStockTimer() throws Exception {
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = CustomQuartzJob.class.getName();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(CustomQuartzJob.class).withIdentity(name,group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name,group).withSchedule(scheduleBuilder).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }
}

