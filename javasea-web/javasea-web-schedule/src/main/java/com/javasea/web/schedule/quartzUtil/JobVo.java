package com.javasea.web.schedule.quartzUtil;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName JobVo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/7/5 0005 17:34
 */
@Data
public class JobVo {
    private String jobName;
    private Date startTime;
    private Date previousFireTime;
    private Date nextFireTime;
    private String cronExpression;
}
