package com.iee.patterns.example.strategy.orderregister.solver.init;

import com.iee.patterns.example.strategy.orderregister.solver.InspectionSolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description 将业务处理器和其支持处理的类型放到一个容器中，java里Map就是最常用的容器之一
 * 这里是在应用启动的时候，加载spring容器中所有InspectionSolver类型的处理器，
 * 放到InspectionSolverChooser的map容器中。注意是InspectionSolver类型，所以定义的处理器都得继承InspectionSolver，
 * 其次是spring容器中的才能加载，所以定义的处理器都得放到spring容器中（@Component注解不能少）
 * @Author longxiaonan@163.com
 * @Date 19:47 2019/6/27 0027
 **/
@Component
public class InspectionSolverChooser implements ApplicationContextAware {


    private Map<String, InspectionSolver> chooseMap = new HashMap<>();

    public InspectionSolver choose(String type) {
        return chooseMap.get(type);
    }

    @PostConstruct
    public void register() {
        Map<String, InspectionSolver> solverMap = context.getBeansOfType(InspectionSolver.class);
        for (InspectionSolver solver : solverMap.values()) {
            for (String support : solver.supports()) {
                chooseMap.put(support,solver);
            }
        }
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }
}
