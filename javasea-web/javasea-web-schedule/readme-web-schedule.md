[TOC]

# springboot开启定时任务

## spring task

### 主类通过注解`@EnableScheduling`开启定时任务

```java
@SpringBootApplication
@EnableScheduling
public class APPlication {
    public static void main(String[] args) {
        SpringApplication.run(APPlication.class, args);
    }
}
```

### 任务类方法上通过注解`@Scheduled`开启任务

```java
@Service
@Slf4j
public class ScheduleService {
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }
}
```

### Schedule 三种任务调度器

Schedule 主要有三种调度方式：fixedRate、fixedDelay、cron表达式。

#### fixedRate

固定频率任务。

该属性的含义是上一个调用开始后再次调用的延时（不用等待上一次调用完成），这样就可能会存在任务重复执行的问题，所以不是建议使用，但数据量如果不大时在配置的间隔时间内可以执行完也是可以使用的。

> 注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。

```
@Scheduled(fixedRate = 1000 * 10)
public void work() {
  // do your work here
  
}
复制代码
```

fixedRate 的单位是毫秒，上例中 `fixedRate = 1000 * 10` 表示每 10 秒执行一次。

设第一次执行开始时时间为 0，正常情况下第二次执行开始时间是第 10 秒，第三次是第 20 秒……以此类推。

但假如任务在 10 秒内没有完成，比如第一次花了 15秒完成任务，那第二次执行时间也是 第15 秒，会马上执行。

#### fixedDelay

固定间隔任务。

下一次的任务执行时间，是从方法最后一次任务执行结束时间开始计算。并以此规则开始周期性的执行任务。

```
@Scheduled(fixedDelay = 1000 * 10)
public void work() {
  // do your work here
  
}
复制代码
```

上例每隔10秒执行一次，设第一次执行开始时时间为 0，如果任务的执行时间是 5 秒，那下次任务的开始时间是 `5 + 10 = 15` 即第 15 秒。再下一次任务开始执行时间是 `15 + 5 + 10 = 30` 即第 30 秒，以此类推。

#### Cron表达式

Cron表达式由6或7个空格分隔的时间字段组成，如下图：



![img](https://user-gold-cdn.xitu.io/2019/8/21/16cb330f799b58d6?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



```
* 第一位，表示秒，取值 0-59
* 第二位，表示分，取值 0-59
* 第三位，表示小时，取值 0-23
* 第四位，日期，取值 1-31
* 第五位，月份，取值 1-12
* 第六位，星期几，取值 1-7
* 第七位，年份，可以留空，取值 1970-2099

(*) 星号：可以理解为“每”的意思，每秒、没分
(?) 问号：只能出现在日期和星期这两个位置，表示这个位置的值不确定
(-) 表达一个范围，如在小时字段中使用 10-12 ，表示从10点到12点
(,) 逗号，表达一个列表值，如在星期字段中使用 1,2,4 ，则表示星期一、星期二、星期四
(/) 斜杠，如 x/y ，x是开始值，y是步长，如在第一位(秒)使用 0/15，表示从0秒开始，每15秒

官方解释：
0 0 3 * * ?         每天 3 点执行
0 5 3 * * ?         每天 3 点 5 分执行
0 5 3 ? * *         每天 3 点 5 分执行
0 5/10 3 * * ?      每天 3 点 5 分，15 分，25 分，35 分，45 分，55 分这几个点执行
0 10 3 ? * 1        每周星期天的 3 点10 分执行，注：1 表示星期天
0 10 3 ? * 1 #3      每个月的第三个星期的星期天 执行，#号只能出现在星期的位置

注：第六位(星期几)中的数字可能表达不太正确，可以使用英文缩写来表示，如：Sun
复制代码
```

注意，当方法的执行时间超过任务调度频率时，调度器会在下个周期执行。

下面的例子，每 10 秒执行一次，但任务执行时间是 12 秒。

设第一次执行任务开始时间是 0，那第一次任务执行完应该是第 12 秒。第二次任务本应在第 10 秒开始执行，但由于第 10 秒时第一次任务还没执行完，所以第二次任务会把第 10 秒这个节点跳过，等到第 20 秒再执行第二次任务。

```
@Scheduled(cron = "0/10 * * * * *")
public void test() throws InterruptedException {
  log.info(Thread.currentThread().getName()+"---test");
  TimeUnit.SECONDS.sleep(12);
}
```

。

## 配置 TaskScheduler 线程池

Spring 会默认创建一个单线程池，如果系统中有多个定时任务要执行，任务调度器就会出现时间漂移，任务执行时间将不确定。

所以我们自定义一个 TaskScheduler 线程池。

```java
@Configuration
public class InitBeanConfig {

  /**
   * 配置 Schedule 的线程池.
   */
  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(10);
    taskScheduler.setThreadNamePrefix("springboot-task");
    return taskScheduler;
  }
}
```



## 多线程方式：

对应示例的包：com.javasea.web.schedule.executorservice

```java
public static void main(String[] args) {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    // 参数：1、任务体 2、首次执行的延时时间
    //      3、任务执行间隔 4、间隔时间单位
    service.scheduleAtFixedRate(()->System.out.println("task ScheduledExecutorService "+new Date()),
            0, 3, TimeUnit.SECONDS);
}
```

## 整合Quartz

###  添加依赖

> 如果SpringBoot版本是2.0.0以后的，则在spring-boot-starter中已经包含了quart的依赖，则可以直接使用`spring-boot-starter-quartz`依赖

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
```

> 如果是1.5.9则要使用以下添加依赖: 

```java
<dependency>
  <groupId>org.quartz-scheduler</groupId>
  <artifactId>quartz</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context-support</artifactId>
</dependency>
```

### 设置配置文件

```java
package com.javasea.web.schedule.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail teatQuartzDetail(){
        return JobBuilder.newJob(TestQuartz.class).withIdentity("testQuartz").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)  //设置时间周期单位秒
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("testQuartz")
                .withSchedule(scheduleBuilder)
                .build();
    }
}

```

### 编写job类

```java
package com.javasea.web.schedule.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
/** job类，继承QuartzJobBean后重写方法即可 */
public class TestQuartz extends QuartzJobBean {
    /**
     * 执行定时任务
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("quartz task "+new Date());
    }
}

```

## 参考：

https://juejin.im/post/5d5cf9fc518825415d061069

