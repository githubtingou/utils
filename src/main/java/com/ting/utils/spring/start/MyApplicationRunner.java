package com.ting.utils.spring.start;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 启动类
 * <p>
 * 在application加载所有bean后执行
 *
 * @author lishuang
 * @version 1.0
 * @date 2021/8/11
 */
@Component
public class MyApplicationRunner implements ApplicationRunner, Ordered {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner--->");

        // 参数是启动传来的参数，如Program arguments中传输的参数 （获取 --开头的参数）
        args.getOptionNames().forEach(System.out::println);

        // 和getOptionNames()的方法相反，就获取不以--开头的参数
        args.getNonOptionArgs().forEach(System.out::println);

        // 参数是启动传来的参数，如Program arguments中传输的参数 （获取前面不加符号的参数）
        Arrays.asList(args.getSourceArgs()).forEach(System.out::println);

        // 判断时候包含该key，(需要注意如果前面有--开头的参数，需要去除--)
        System.out.println("args.containsOption(\"name\") = " + args.containsOption("name"));

        // 根据key获取参数,(需要注意如果前面有--开头的参数，需要去除--)
        System.out.println("args.getOptionValues(\"name\") = " + args.getOptionValues("name"));

        System.out.println("ApplicationRunner--->");
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
