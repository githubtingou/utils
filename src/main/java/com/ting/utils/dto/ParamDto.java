package com.ting.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
