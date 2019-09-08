package com.iee.enumm;

/**
 * Created by yuanqiang on 2017/11/13.
 */
public interface EventStateConstKey {

    /**
     * 点熄火事件状态
     */
    enum VSTSTE {
        //0x01：车辆启动状态；0x02：熄火；0x03：其他状态；“0xFE”表示异常，“0xFF”表示无效
        STARTUP(10001), // 点火
        STALL(10002), // 熄火
        OTHER(10003), // 其他
        ERROR(10254), // 错误
        INVALID(10255) ; // 无效

        int value;
        private VSTSTE(int value){
        	this.value = value;
        }
    	public int getValue() {
    		return this.value;
    	}
    }

    /**
     * 车辆充电状态
     */
    enum CHARGE {
        //0x01：停车充电；0x02：行驶充电；0x03：未充电状态；0x04：充电完成；“0xFE”表示异常，“0xFF”表示无效
        STOP_CHARGE(20001),
        START_CHARGE(20002),
        NOT_CHARGE(20003),
        COMPLETE_CHARGE(20004),
        ERROR(10254 ), // 错误
        INVALID(10255); // 无效

        int value;
        private CHARGE(int value){
        	this.value = value;
        }
    	public int getValue() {
    		return this.value;
    	}
    }


}
