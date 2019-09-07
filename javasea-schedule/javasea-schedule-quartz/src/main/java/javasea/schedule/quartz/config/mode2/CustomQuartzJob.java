package javasea.schedule.quartz.config.mode2;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author ：F嘉阳
 * @date ：2019/7/29 8:55
 */
@Slf4j
//@DisallowConcurrentExecution
public class CustomQuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("执行定时任务");
    }
}
