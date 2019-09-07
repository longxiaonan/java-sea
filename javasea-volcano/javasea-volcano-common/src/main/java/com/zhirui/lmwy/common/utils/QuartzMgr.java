package com.zhirui.lmwy.common.utils;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;


/**
 * Quartz的管理器
 * <li>这里的JobDetail所对应的CronTrigger用的名字是JobDetail的名字</li>
 * @author lijian@cstonline.cn
 * @author longxn
 *
 * @date 2014年12月15日 下午2:34:38
 */
@Slf4j
public class QuartzMgr {

	/**JobDetail的默认组名称*/
	private static final String JOBDETAIL_DEFAULT_GROUP = "JOBDETAIL_DEFAULT_GROUP";

	/**CronTrigger的默认组名称*/
	private static final String CRONTRIGGER_DEFAULT_GROUP = "CRONTRIGGER_DEFAULT_GROUP";

	/**SchedulerFactory,单例即可*/
	private static final SchedulerFactory SF = new StdSchedulerFactory();

	private static final QuartzMgr INSTANCE = new QuartzMgr();

	/** SimpleTrigger的间隔模式, 分别对应simpleTrigger的每隔多少毫秒, 多少秒钟, 多少分钟, 多少小时, */
	public enum IntervalMode {
		Milliseconds, Seconds, Minutes, Hours
    }

	private QuartzMgr(){}

	public static QuartzMgr getInstance() {
		return INSTANCE;
	}

	/**
	 * 添加一个简单类型的job, 可以通过指定间隔多少秒, 多少分钟, 多少小时执行, 还可以指定是否立即执行
	 * @param jobname
	 *            job的名称
	 * @param jobClass
	 *            job的class
	 * @param intervalMode
	 *            执行间隔的模式: IntervalMode类型的枚举量, 表示间隔多少分钟多少小时等
	 * @param value
	 *            间隔的数值, 如:intervalMode为分钟时, 表示间隔多少分钟; intervalMode为小时时,
	 *            表示间隔多少秒钟
	 * @param isStartNow
	 *            是否立即执行
	 */
	public void addSimpleJob(String jobname, Class<? extends Job> jobClass, IntervalMode intervalMode, int value,
			boolean isStartNow) throws SchedulerException {

		if (org.apache.commons.lang3.StringUtils.isBlank(jobname) || jobClass == null) {
			return;
		}

		JobDetail jobdetail = JobBuilder.newJob(jobClass).withIdentity(jobname, JOBDETAIL_DEFAULT_GROUP).build();

		SimpleScheduleBuilder simpleSchedule = SimpleScheduleBuilder.simpleSchedule().repeatForever();

		// 判断间隔模式是: 秒, 分钟, 小时; 判断后添加相应的数值
		switch (intervalMode) {
		case Milliseconds:
			simpleSchedule.withIntervalInMilliseconds(value);
			break;
		case Seconds:
			simpleSchedule.withIntervalInSeconds(value);
			break;
		case Minutes:
			simpleSchedule.withIntervalInMinutes(value);
			break;
		case Hours:
			simpleSchedule.withIntervalInHours(value);
			break;
		default:
		}

		SimpleTrigger trigger = null;

		// 判断是否立即启动
		if (isStartNow) {
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(jobname, CRONTRIGGER_DEFAULT_GROUP).withSchedule(simpleSchedule).startNow().build();
		} else {
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(jobname, CRONTRIGGER_DEFAULT_GROUP).withSchedule(simpleSchedule).build();
		}

		Scheduler scheduler = SF.getScheduler();

		scheduler.scheduleJob(jobdetail, trigger);

		scheduler.start();

		log.info("add job,jobname[{}] jobClass[{}] interval mode[{}] intertime[{}]", jobname, jobClass, intervalMode,
				value);
	}

	/**
	 * 添加一个job
	 * @param jobname    job的名称
	 * @param jobClass   job的class
	 * @param cronExpression   "* * * * * ?"
	 * @throws SchedulerException
	 */
	public void addCronJob(String jobname,Class<? extends Job> jobClass,String cronExpression) throws SchedulerException {

		if(StringUtils.isBlank(jobname) || jobClass == null || StringUtils.isBlank(cronExpression) ) {
			return;
		}

		JobDetail jobdetail =  JobBuilder.newJob(jobClass).withIdentity(jobname, JOBDETAIL_DEFAULT_GROUP).build();

		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobname, CRONTRIGGER_DEFAULT_GROUP)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

		Scheduler scheduler =  SF.getScheduler();

		scheduler.scheduleJob(jobdetail,trigger);

		scheduler.start();

		log.info("add job,jobname[{}] jobClass[{}] time[{}]",jobname,jobClass,cronExpression);
	}


	/**
	 * 移除一个jop
	 * @param jobname job的名称
	 * @throws SchedulerException
	 */
	public void removeJob(String jobname) throws SchedulerException {

		if(StringUtils.isBlank(jobname)){
			return;
		}

		Scheduler scheduler =  SF.getScheduler();

		//停止触发器
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobname, CRONTRIGGER_DEFAULT_GROUP));
		//移除触发器
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobname, CRONTRIGGER_DEFAULT_GROUP));
		//删除任务
		scheduler.deleteJob(JobKey.jobKey(jobname, JOBDETAIL_DEFAULT_GROUP));

		log.info("remove job,jobname[{}]",jobname);

	}


	/**
	 * 修改某个job的cron设置的时间
	 * @param jobname job的名称
	 * @param time	  设置的cron时间
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void modifyJobTime(String jobname,String time) throws SchedulerException, ParseException {

		if(StringUtils.isBlank(jobname) || StringUtils.isBlank(time)){
			return;
		}

		log.info("modify job time,jobname[{}] new time[{}]",jobname,time);

		Scheduler scheduler =  SF.getScheduler();
		Class<? extends Job> jobclass = scheduler.getJobDetail(JobKey.jobKey(jobname, JOBDETAIL_DEFAULT_GROUP)).getJobClass();

		//先移除
		removeJob(jobname);
		//然后添加
		addCronJob(jobname,jobclass,time);
	}

	public static void main(String[] args) throws SchedulerException, ParseException {
		//添加简单任务
//		QuartzMgr.getInstance().addSimpleJob("a",QuartzMgr.TestJob.class, IntervalMode.Seconds, 2, false);
		//添加cron任务
		QuartzMgr.getInstance().addCronJob("b", QuartzMgr.TestJob.class, "* * * * * ?");
		//修改cron任务
		QuartzMgr.getInstance().modifyJobTime("b", "0/3 * * * * ? ");
	}


	@Slf4j
	public static class TestJob implements Job {

		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			System.out.println("hello job");
		}
	}

}

