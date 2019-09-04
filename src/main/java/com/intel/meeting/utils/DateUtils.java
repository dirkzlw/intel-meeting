package com.intel.meeting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ranger
 * @create 2019-09-03 21:37
 */
public class DateUtils {

    public static Date stringToDate(SimpleDateFormat sdf,String time){

        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
