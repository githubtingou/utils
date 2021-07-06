package com.ting.utils.spi.impl;

import com.ting.utils.spi.Search;

/**
 * 文件中查询
 *
 * @author ls
 * @version 1.0
 * @date 2021/7/6
 */
public class FileSearchImpl implements Search {
    @Override
    public void getName(String key) {
        System.out.println("文件中查询--->" + key);
    }
}
