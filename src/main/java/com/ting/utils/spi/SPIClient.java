package com.ting.utils.spi;

import java.util.ServiceLoader;

/**
 * @author ls
 * @version 1.0
 * @date 2021/7/6
 */
public class SPIClient {

    public static void main(String[] args) {
        ServiceLoader<Search> serviceLoader = ServiceLoader.load(Search.class);
        for (Search search : serviceLoader) {
            search.getName("spi");
        }
    }
}
