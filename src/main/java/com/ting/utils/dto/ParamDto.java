package com.ting.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 简单的实体类
 *
 * @author ls
 * @version 1.0
 * @date 2021/6/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamDto<K, V> {
    private K key;
    private V value;
    private K groupKey;

    static Integer a = 0;
    static Integer b = 0;

    public static void main(String[] args) {
        // 死锁

        Thread thread = new Thread(() -> {
            if (b == 10) {
                a = 10;

            }
        });
        Thread thread1 = new Thread(() -> {
            if (a == 10) {
                b = 10;

            }
        });
        thread.start();
        thread1.start();

    }

}
