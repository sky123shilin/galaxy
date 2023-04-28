package org.galaxy.util.date;

import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Shilin.Qu
 * @version V1.0
 * @Package com.galaxy.util.date
 * @date 2021/12/14 12:57
 *
 * 备注：都是使用java8 新的时间jar包来处理的
 */
public final class DateUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private DateUtils(){}

    /**
     * 获取当前时间距离一天结束的剩余秒数
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * 获取两个时间相差的年份
     * @param date01
     * @param date02
     * @return
     */
    public static Integer getYearsBetweenTwoInstant(Instant date01, Instant date02){
        if(date01 == null || date02 == null){
            return 0;
        }
        LocalDateTime date001 = LocalDateTime.ofInstant(date01, ZoneId.systemDefault());
        LocalDateTime date002 = LocalDateTime.ofInstant(date02, ZoneId.systemDefault());
        long years = 0L;
        if(date001.isAfter(date002)){
            years = ChronoUnit.YEARS.between(date002,date001);
        }else{
            years = ChronoUnit.YEARS.between(date001,date002);
        }
        return (int) years;
    }

    /**
     * 将一个日期时间字符串转化成Instant实例
     * @param dateTimeStr 日期时间字符串
     * @return
     */
    public static Instant convertFromStringToInstant(String dateTimeStr){
        if(!StringUtils.hasText(dateTimeStr)) return null;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //将 string 装换成带有 T 的国际时间，但是没有进行，时区的转换，即没有将时间转为国际时间，只是格式转为国际时间
        LocalDateTime parse = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
        //+8 小时，offset 可以理解为时间偏移量
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        //转换为 通过时间偏移量将 string -8小时 变为 国际时间，因为亚洲上海是8时区
        return parse.toInstant(offset);
    }

    /**
     * 将一个日期时间字符串转化成Instant实例
     * @param dateStr  日期字符串
     * @param timeStr  时间字符串
     * @return
     */
    public static Instant convertFromStringToInstant(String dateStr, String timeStr){
        if(!StringUtils.hasText(dateStr)) return null;
        timeStr = StringUtils.hasText(timeStr) ? timeStr : "00:00:00";
        return convertFromStringToInstant(dateStr.concat(" ").concat(timeStr));
    }

    /**
     * 校验日期字符串是否满足"yyyy-MM-dd"规则
     * @param dateStr
     * @return
     */
    public static Boolean isValidPattern(String dateStr) {
        return isValidPattern(dateStr, DateUtils.DATE_PATTERN);
    }

    /**
     * 校验日期字符串是否满足规则
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Boolean isValidPattern(String dateStr, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        boolean flag = true;
        //Java 8 新添API 用于解析日期和时间
        try {
            LocalDateTime.parse(dateStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            flag = false;
        }
        return flag;
    }
}
