package com.intel.meeting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ranger
 * @create 2019-09-03 21:37
 */
public class DateUtils {
    public static Date dateTimeToDate(SimpleDateFormat sdf,String date,String time){

        Date rtn = null;
        try {
            rtn = sdf.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
