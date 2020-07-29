package event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName JobMgr
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/7/5 0005 21:44
 */
@Component
public class JobMgr implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    TestService testService;

    // @PostConstruct bean还没注入，不能使用该方式
    @PostConstruct
    public void init()  {

    }

    private void initSummaryScreenJob()  {
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("testService=："+ testService);
    }

}
