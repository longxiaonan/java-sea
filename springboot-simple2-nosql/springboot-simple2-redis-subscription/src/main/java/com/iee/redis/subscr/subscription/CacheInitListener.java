package com.iee.redis.subscr.subscription;

import com.iee.redis.subscr.publish.PubSubManager;
import com.iee.redis.subscr.publish.InfoService;
import com.iee.redis.subscr.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 加载系统参数
 */
public class CacheInitListener implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(CacheInitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        logger.info("---CacheListener初始化开始---");
        init();
        logger.info("---CacheListener初始化结束---");
    }

    public void init() {
        try {
            //获得管理器
            PubSubManager pubSubManager = SpringContextUtil.getBean(PubSubManager.class);

            InfoService infoService = SpringContextUtil.getBean(InfoService.class);
            //添加到管理器
            pubSubManager.addListener("infoService", infoService);
            //other service...

            //启动线程执行订阅操作
            pubSubManager.start();
            //初始化加载
            loadParamToRedis();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    private void loadParamToRedis() {
        InfoService infoService = SpringContextUtil.getBean(InfoService.class);
        infoService.update();
        //other service...
    }
}
