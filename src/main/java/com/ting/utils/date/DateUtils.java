package com.ting.utils.date;

import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 *
 * @author ls
 * @version 1.0
 * @date 2021/6/23
 */
public class DateUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_STR = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_FORMATTER_STR = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_FORMATTER_MINUTE = DateTimeFormatter.ofPattern("yyyyMMddHHmm");


    /**
     * localDate转为LocalDateTime
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime localDate2LocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();

    }

    /**
     * localDate转为LocalDateTime
     *
     * @param localDate
     * @return
     */
    public static LocalDate localDateTime2LocalDate(LocalDateTime localDate) {
        return localDate.toLocalDate();

    }

    /**
     * 时间格式转换
     *
     * @param localDateTime {@link LocalDateTime}时间
     * @return {String}  yyyy-MM-dd HH:mm:ss
     */
    public static String timeFormatter(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime, "参数不能为空");
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 获取俩个时间段的所有时间
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return List<String>  "yyyy-MM-dd
     * @throws IllegalArgumentException start.isAfter(end) == true
     */
    public static List<String> getDates(LocalDate start, LocalDate end) {
        Assert.isTrue(start.isBefore(end), "开始时间不能大于结束事件");
        List<String> list = new ArrayList<>();
        while (start.isAfter(end)) {
            list.add(start.plusDays(1L).format(DATE_FORMATTER));

        }
        return list;
    }

    /**
     * Date转localDateTime
     *
     * @param date {@link Date} 需要转换的时间
     * @return {@link LocalDateTime} 转化后的时间
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * localDateTime转Date
     *
     * @param localDateTime {@link LocalDateTime} 需要转换的时间
     * @return {@link Date} 转化后的时间
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }


    /**
     * Date转localDate
     *
     * @param date {@link Date} 需要转换的时间
     * @return {@link LocalDate} 转化后的时间
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * localDate转Date
     *
     * @param localDate {@link LocalDate} 需要转换的时间
     * @return {@link Date} 转化后的时间
     */
    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * 日期格式转换
     *
     * @param localDate {@link LocalDate}时间
     * @return {String}  yyyy-MM-dd
     */
    public static String dateFormatter(LocalDate localDate) {
        Assert.notNull(localDate, "参数不能为空");
        return localDate.format(DATE_FORMATTER);
    }

    /**
     * 生成时间段
     *
     * @param interval  {@link Long} 时间间隔
     * @param timeUnit  {@link ChronoUnit}时间单位
     * @param startTime {@link LocalDateTime}开始时间
     * @param endTime   {@link LocalDateTime} 结束时间
     * @return {@link List<String>}  例子：<code>[2021-06-23 00:00:00-2021-06-23 01:00:00]</code>
     * @see LocalDateTime 时间类
     */
    public static List<String> timeSlot(Long interval, ChronoUnit timeUnit, LocalDateTime startTime, LocalDateTime endTime) {
        Assert.notNull(interval, "参数不能为空");
        Assert.notNull(timeUnit, "参数不能为空");
        Assert.notNull(startTime, "参数不能为空");
        Assert.notNull(endTime, "参数不能为空");

        List<String> list = new ArrayList<>();
        while (endTime.isAfter(startTime)) {
            LocalDateTime nowPlusTime = startTime;
            startTime = startTime.plus(interval, timeUnit);
            list.add(nowPlusTime.format(DATE_TIME_FORMATTER) + "-" + startTime.format(DATE_TIME_FORMATTER));
        }
        return list;

    }

    /**
     * 获取当前日期的时间间隔
     *
     * @param interval {@link Long} 时间间隔
     * @param timeUnit {@link TimeUnit}时间单位
     * @return {@link List<String>} 例子：<code>[2021-06-23 00:00:00-2021-06-23 01:00:00]</code>
     * @see ChronoUnit <code>TemporalUnit</code>的实现类
     * @see LocalDateTime 时间类
     */
    public static List<String> timeSlot(Long interval, ChronoUnit timeUnit) {
        LocalDate date = LocalDate.now();
        return timeSlot(interval, timeUnit, date, date);
    }

    /**
     * 获取当前日期的时间间隔
     *
     * @param interval {@link Long} 时间间隔
     * @param timeUnit {@link TimeUnit}时间单位
     * @return {@link List<String>} 例子：<code>[2021-06-23 00:00:00-2021-06-23 01:00:00]</code>
     * @see ChronoUnit <code>TemporalUnit</code>的实现类
     * @see LocalDateTime 时间类
     */
    public static List<String> timeSlot(Long interval, ChronoUnit timeUnit, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.MAX);
        return timeSlot(interval, timeUnit, startTime, endTime);
    }


    public static void main(String[] args) {

        System.out.println("timeSlot = " + timeSlot(1L, ChronoUnit.HOURS, LocalDateTime.now(), LocalDateTime.now()));

        System.out.println("date2LocalDate = " + date2LocalDate(new Date()).format(DATE_FORMATTER));

        System.out.println("localDate2Date = " + localDate2Date(LocalDate.now()));

        System.out.println("date2LocalDateTime = " + date2LocalDateTime(new Date()).format(DATE_TIME_FORMATTER));

        System.out.println("localDateTime2Date = " + localDateTime2Date(LocalDateTime.now()));

        System.out.println("localDate2LocalDateTime = " + localDate2LocalDateTime(LocalDate.now()));

        System.out.println("localDateTime2LocalDate = " + localDateTime2LocalDate(LocalDateTime.now()));

        System.out.println("getDates = " + getDates(LocalDate.now().plusDays(-10L), LocalDate.now()));
    }


}
