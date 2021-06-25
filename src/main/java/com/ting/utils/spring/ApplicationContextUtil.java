package com.ting.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * ApplicationContext工具类
 *
 * @author ls
 * @version 1.0
 * @date 2021/6/24
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean名称获取bean
     *
     * @param name bean name
     * @param <T>  泛型
     * @return class
     */
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 根据bean名称和类型获取bean
     *
     * @param name         bean name
     * @param requiredType bean 的类型，如父类
     * @param <T>          泛型
     * @return class
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    /**
     * 根据类型查询所有的beanName
     *
     * @param type {@link Class<T>}  class
     * @param <T>  泛型
     * @return {@link List<String>}所有class所属的bean name集合
     */
    public static <T> List<String> getBeanNamesByType(Class<T> type) {
        return Arrays.asList(getApplicationContext().getBeanNamesForType(type));

    }

}