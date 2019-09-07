package com.javasea.web.aspect.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    //也可以通过切点来实现
    @Pointcut("execution(* com.javasea.web.aspect.aop.Performance.perform(..))")
    public void per(){};


    //表演前 手机静音
    @Before("per()")
    public void silenceCellPhone(){
        System.out.println("silence Cell Phone");
    }

    //表演成功-clap
    @AfterReturning("per()")
    public void clap(){
    System.out.println("clap clap clap");
    }

    //表演失败-退款, 如果service方法中抛出异常时才生效
    @AfterThrowing("execution(* com.javasea.web.aspect.aop.Performance.perform(..))")
    public void refund(){
        System.out.println("refund refund refund");
    }

    /**首先注意到的可能是它接受ProceedingJoinPoint作为参数。这个对象是必须要有的，因为你要在通知中通过它来调用被通知的方法。通知方法中可以做任何的事情，当要将控制权交给被通知的方法时，它需要调用ProceedingJoinPoint的proceed()方法。
     如果不调proceed()这个方法的话，那么你的通知实际上会阻塞对被通知方法的调用。同样的，你也可以调用多次。
     **/
    @Around("per()")
    public void watch(ProceedingJoinPoint point) throws Throwable {
        try{
            System.out.println("silence Cell Phone");
            point.proceed();
            System.out.println("clap clap clap");
        }catch (Exception e){
            System.out.println("refund refund refund");
        }
    }

}
