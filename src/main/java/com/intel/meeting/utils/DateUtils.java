package com.intel.meeting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Intel-Meeting
 * @create 2019-09-03 21:37
 */
public class DateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    /**
     * 字符日期转换为Date
     *
     * @param time String类型的2019-08-08 05:05
     * @return 返回Date类型数据
     */
    public static Date stringToDate(String time) {

        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符日期转换为时间戳
     * 解决12:XX转换为00：XX的bug
     *
     * @param time  String类型的2019-08-08 05:05
     * @return  返回对应的时间戳
     */
    public static long stringToTime(String time) {

        try {
            long rtn = sdf.parse(time).getTime();
            if ("12".equals(time.split(" ")[1].split(":")[0])) {
                rtn += 43200000;
            }
            return rtn;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取三天后时间 返回String类型
     *
     * @return  返回三天后String类型日期
     */
    public static String getAfterThreeDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, +3);
        Date rtnDate = c.getTime();
        return sdf.format(rtnDate);
    }

    public static int getWeekOfTime(String time) {
        Calendar c = Calendar.getInstance();
        Date date = DateUtils.stringToDate(time);
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }
}
