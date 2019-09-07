package com.intel.meeting;

import com.intel.meeting.utils.DateUtils;

import java.util.Date;

/**
 * @author Ranger
 * @create 2019-09-03 21:43
 */
public class SETest {
    public static void main(String[] args) {
        //获取三天后时间
        String s1 = "2019-09-07 12:01";
        String s2 = "2019-09-07 24:00";
        long l1 = DateUtils.stringToTime(s1);
        Date d1 = DateUtils.stringToDate(s1);
        System.out.println("d1 = " + d1);
        System.out.println("l1 = " + l1);
        long l2 = DateUtils.stringToTime(s2);
        Date d2= DateUtils.stringToDate(s2);
        System.out.println("d2 = " + d2);
        System.out.println("l2 = " + l2);
    }
}
