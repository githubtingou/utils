package com.ting.utils.stream;

import com.ting.utils.date.DateUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * stram常用或好用的方法
 *
 * @author ls
 * @version 1.0
 * @date 2021/6/23
 */
public class StreamUtils {

    /**
     * 计算集合数据中的数值数据
     *
     * @param collection      {@link Collection<T>}       需要求和的集合
     * @param function        {@link Function<T, BigDecimal>} 数据转换
     * @param decimal         {@link BigDecimal} 起始数据
     * @param decimalFunction {@link BinaryOperator<BigDecimal>}  BigDecima方法 相加等计算方法
     * @param <T>             泛型
     * @return {@link BigDecimal} 计算后的数据
     */
    public static <T> BigDecimal calculationCollection(Collection<T> collection, Function<T, BigDecimal> function, BigDecimal decimal, BinaryOperator<BigDecimal> decimalFunction) {
        Assert.notEmpty(collection, "数据不能为空");
        return collection.stream()
                .map(function)
                .reduce(decimal, decimalFunction);
    }

    /**
     * 数据转换
     *
     * @param list     {@link List<T>} 需要转换的数据
     * @param function {@link Function<T, R>} 转换的方法
     * @param <T>      源数据
     * @param <R>      转换后的数据
     * @return 转换后的集合
     */
    public static <T, R> List<R> dataConversion2List(Collection<T> list, Function<T, R> function) {
        Assert.notEmpty(list, "数据不能为空");
        return list.stream()
                .map(function)
                .collect(Collectors.toList());

    }


    /**
     * 根据时间间隔分组
     *
     * @param {@link List<Date>} 入参
     * @return {@link Map<String, List<Date>>} key:时间；value:分组的数据
     * @see LocalDateTime#truncatedTo(TemporalUnit)  分隔方法
     * @see DateUtils#DATE_FORMATTER_MINUTE 时间的序列化，即key存入的值
     * @see Collectors#groupingBy(Function) 分组方法
     */
    public static Map<String, List<Date>> handleDataByTime(List<Date> list) {
        Assert.notEmpty(list, "数据不能为空");
        return list.stream().collect(
                Collectors.groupingBy(
                        item -> {
                            Instant instant = item.toInstant();

                            LocalDateTime time = instant
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                            // 存储分钟的小时字段
                            int minutes = time.getMinute();
                            // 确定我们是多少分钟高于最近的分钟间隔。
                            int minutesOver = minutes % 5;
                            // 将时间截断到分钟字段（将秒和纳秒归零），
                            // 并强制将分钟数设置为 minutesOver 分钟间隔
                            return time.truncatedTo(ChronoUnit.MINUTES)
                                    .withMinute(minutes - minutesOver)
                                    .format(DateUtils.DATE_FORMATTER_MINUTE);
                        }

                )
        );

    }

    public static void main(String[] args) {
        List<BigDecimal> list = new ArrayList<BigDecimal>() {{
            add(BigDecimal.valueOf(1));
            add(BigDecimal.valueOf(2));
            add(BigDecimal.valueOf(3));
            add(BigDecimal.valueOf(4));

        }};
        BigDecimal bigDecimal = calculationCollection(list, Function.identity(), BigDecimal.ZERO, BigDecimal::add);
        System.out.println("calculationCollection = " + bigDecimal);

        List<BigDecimal> list1 = dataConversion2List(list, Function.identity());
        System.out.println("dataConversion2List = " + Arrays.toString(list1.toArray()));


        List<Date> date = new ArrayList<Date>() {{
            Date date1 = new Date();

            add(date1);
            add(date1);
            add(date1);
            add(date1);

        }};
        Map<String, List<Date>> stringListMap = handleDataByTime(date);
        System.out.println("handleDataByTime = " + stringListMap.toString());
    }
}
