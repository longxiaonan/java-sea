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

