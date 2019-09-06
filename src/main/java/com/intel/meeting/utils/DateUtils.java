package com.intel.meeting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ranger
 * @create 2019-09-03 21:37
 */
public class DateUtils {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    /**
     * 字符日期转换为Date
     * @param time
     * @return
     */
    public static Date stringToDate(String time){

        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符日期转换为时间戳
     * @param time
     * @return
     */
    public static long stringToTime(String time){

        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
