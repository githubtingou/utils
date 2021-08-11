package com.ting.utils.spring.start;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * CommandLineRunner
 * <p>
 * 在spring的bean启动完成后执行
 *
 * @author lishuang
 * @version 1.0
 * @date 2021/8/11
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner, Ordered {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("commandLineRunner---->");

        // 可以获取Program arguments中传输的参数
        Arrays.stream(args).forEach(System.out::println);

        System.out.println("commandLineRunner---->");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
