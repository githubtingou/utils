package com.ting.utils.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * redis工具类
 *
 * @author ls
 * @version 1.0
 * @date 2021/6/28
 */
@Component
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 将map封装到hash中
     *
     * @param name
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> boolean setValueByMap(String name, Map<K, V> map) {
        try {
            redisTemplate.opsForHash().putAll(name, map);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 根据字段更新数据
     *
     * @param name
     * @param field
     * @param value
     * @return
     */
    public boolean updateValueByField(String name, Object field, Object value) {
        try {
            redisTemplate.opsForHash().put(name, field, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 新增字段更新数据
     *
     * @param name
     * @param field
     * @param value
     * @return
     */
    public boolean setValueByField(String name, Object field, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(name, field, value);


    }

    /**
     * String
     *
     * @param name
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean setValue(String name, T t) {
        try {
            redisTemplate.opsForValue().set(name, t);
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;

    }


    /**
     * 从左边插入全部数据
     *
     * @param name
     * @param list
     * @param <T>
     * @return
     */
    public <T> boolean setLeftAllList(String name, List<T> list) {
        Assert.notEmpty(list, "数据不能为空");
        Long aLong = redisTemplate.opsForList().leftPushAll(name, list);
        if (aLong > 0) {
            return true;
        }
        return false;

    }

    /**
     * 从右边插入
     *
     * @param name
     * @param list
     * @param <T>
     * @return
     */
    public <T> boolean setRightAllList(String name, List<T> list) {
        Assert.notEmpty(list, "数据不能为空");
        Long aLong = redisTemplate.opsForList().rightPushAll(name, list);
        if (aLong > 0) {
            return true;
        }
        return false;

    }

    /**
     * EX操作
     *
     * @param time
     * @param name
     * @param value
     * @return
     */
    public boolean setEX(String name, String value, long time) {
        Object execute = redisTemplate.execute((RedisCallback<Object>) connection ->
                connection.setEx(name.getBytes(StandardCharsets.UTF_8), time, value.getBytes(StandardCharsets.UTF_8))
        );
        return !ObjectUtils.isEmpty(execute);
    }

    /**
     * NX操作
     *
     * @param name
     * @param value
     * @return
     */
    public boolean setNX(String name, String value) {
        Object execute = redisTemplate.execute((RedisCallback<Object>) connection ->
                connection.setNX(name.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8))
        );
        return !ObjectUtils.isEmpty(execute);
    }

    /**
     * 删除
     *
     * @param name
     * @return
     */
    public boolean delete(String... name) {
        Assert.notEmpty(name, "数据不能为空");
        Set<String> collect = Arrays.stream(name).collect(Collectors.toSet());
        Long aLong = redisTemplate.delete(collect);

        if (!ObjectUtils.isEmpty(aLong) || aLong == collect.size()) {
            return true;
        }
        return false;
    }

}
