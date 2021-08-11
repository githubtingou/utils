package com.ting.utils.spring.start;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化
 *
 * @author lishuang
 * @version 1.0
 * @date 2021/8/11
 */
@Component
public class MyInit implements InitializingBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化所有必要的bean后执行该方法
        System.out.println("afterPropertiesSet--->");
    }
}
