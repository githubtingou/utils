package com.ting.utils.spring.start;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听器
 *
 * @author lishuang
 * @version 1.0
 * @date 2021/8/11
 */
@Component
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        /*
         监听器，观察状态，相比较其他的初始化更加灵活，可以监听其他的事件，
         如spring boot 重启，关闭等事件,可以直接在泛型定义具体的事件
         */
        System.out.println("ApplicationListener名称--->" + event.getClass().getName());

        // spring boot 停止后执行
        if (event instanceof ContextStoppedEvent) {
            System.out.println("ContextStoppedEvent--->spring boot 停止执行");

        }

        // spring boot 启动完成后执行
        if (event instanceof ApplicationStartedEvent) {
            System.out.println("ApplicationStartedEvent--->spring boot 启动完成后执行");

        }

        // spring boot 关闭后执行
        if (event instanceof ContextClosedEvent) {
            System.out.println("ContextClosedEvent--->spring boot 关闭后执行");

        }

        // spring boot 重启时执行
        if (event instanceof ContextRefreshedEvent) {
            System.out.println("ContextRefreshedEvent--->spring boot 重启时执行");

        }

    }

}
