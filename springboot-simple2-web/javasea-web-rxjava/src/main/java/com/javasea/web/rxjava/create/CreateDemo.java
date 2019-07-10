package com.javasea.web.rxjava.create;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName CreateDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/8 0008 14:26
 */
@Slf4j
public class CreateDemo {
    public static void main(String[] args) {
        /** 创建 ObservableOnSubscribe 并重写其 subscribe 方法，就可以通过 ObservableEmitter 发射器向观察者发送事件。*/
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello Observer");
                e.onComplete();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                log.info("chan","=============onNext " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                log.info("chan","=============onComplete ");
            }
        };

        observable.subscribe(observer);
    }

}
