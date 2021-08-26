package com.ting.utils.controller;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lishuang
 * @version 1.0
 * @date 2021/8/25
 */
@RestController
@RequestMapping("/geo")
public class GEOController {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String KEY = "geo:ting";

    public GEOController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * Point x:经度
     * y:维度
     * <p>
     * 有效经度为 -180 到 180 度。
     * 有效纬度是从 -85.05112878 到 85.05112878 度。
     *
     * @param map
     */
    @PostMapping(value = "add")
    public void add(@RequestBody Map<String, Point> map) {
        stringRedisTemplate.opsForGeo().add(KEY, map);
    }

    @PostMapping(value = "delete")
    public void delete(@RequestBody String[] names) {
        stringRedisTemplate.opsForGeo().remove(KEY, names);
    }

    /**
     * @param x        经度
     * @param y        维度
     * @param distance 距离
     * @return
     */

    @GetMapping(value = "get/{x}/{y}/{distance}")
    public Object get(@PathVariable(value = "x") Double x,
                      @PathVariable(value = "y") Double y,
                      @PathVariable(value = "distance") double distance) {


        // 设置输出的参数
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .sortAscending()// 排序
                //.limit(3)// 输出元素的个数
                .includeCoordinates()// 输出经纬度
                .includeDistance();// 距离
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius = stringRedisTemplate.opsForGeo()
                .radius(
                        KEY,
                        new Circle(new Point(x, y), new Distance(distance, Metrics.KILOMETERS)),
                        geoRadiusCommandArgs
                );
        assert radius != null;
        if (!CollectionUtils.isEmpty(radius.getContent())) {
            radius.getContent().forEach(item -> {
                System.out.println("item.getContent().getPoint() = " + item.getContent().getPoint());
                System.out.println("item.getContent().getName() = " + item.getContent().getName());

            });

            return radius.getContent();

        }
        return null;
    }

    @GetMapping(value = "getByName/{name}")
    public List<Point> getByName(@PathVariable(value = "name") String name) {
        return stringRedisTemplate.opsForGeo().position(KEY, name);
    }

    @GetMapping(value = "getByNameList")
    public List<Point> getByNameList(@RequestBody String[] name) {
        return stringRedisTemplate.opsForGeo().position(KEY, name);
    }


}



