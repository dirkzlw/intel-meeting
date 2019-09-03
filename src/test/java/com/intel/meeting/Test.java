package com.intel.meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ranger
 * @create 2019-09-03 21:43
 */
public class Test {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date1 = sdf.parse("2019-05-06 05:05");
        System.out.println("date1 = " + date1);
    }
}
