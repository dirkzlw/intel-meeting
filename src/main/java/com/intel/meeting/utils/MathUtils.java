package com.intel.meeting.utils;

import java.text.DecimalFormat;

/**
 * 计算工具类
 *
 * @author Ranger
 * @create 2019-09-08 20:11
 */
public class MathUtils {

    private static DecimalFormat df = new DecimalFormat(".00");

    /**
     * 计算百分率
     *
     * @param zi
     * @param mu
     * @return
     */
    public static String getRate(long zi, long mu) {
        double rate = zi * 100.00 / mu ;
        String rtn = df.format(rate);
        return rtn;
    }
}
