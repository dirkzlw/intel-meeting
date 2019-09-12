package com.intel.meeting;

import com.intel.meeting.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Intel-Meeting
 * @create 2019-09-03 21:43
 */
public class SETest {
    public static void main(String[] args) {
        //获取三天后时间
        String s1 = "2019-09-07 12:01";
        Date d1 = DateUtils.stringToDate(s1);
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        int i = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("i = " + i);
    }
}
