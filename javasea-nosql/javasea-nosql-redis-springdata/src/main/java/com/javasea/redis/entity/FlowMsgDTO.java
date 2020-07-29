package com.javasea.redis.entity;

import lombok.Data;

/**
 * @ClassName FlowMsgDTO
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/7/22 0022 16:00
 */
@Data
public class FlowMsgDTO {
    /** 分类 */
    private String category;
    /** 单号 */
    private String orderCode;
    /** 流程业务实例id */
    private String flowId;
    /** 流程状态 */
    private String state;
}
