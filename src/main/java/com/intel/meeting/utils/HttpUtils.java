package com.intel.meeting.utils;

/**
 * @author Ranger
 * @create 2019-09-11 14:10
 */
public class HttpUtils {

    /**
     * 根据错误代码获取错误信息
     *
     * @param code 403 404...
     * @return
     */
    public static String getMessageByCode(int code) {
        String rtn = "很抱歉，出现了无法识别的错误";
        switch (code) {
            case 403:
                rtn = "很抱歉，服务器拒绝了请求";
                break;
            case 404:
                rtn = "很抱歉，您访问的页面找不到了";
                break;
            case 405:
                rtn = "很抱歉，您访问的方法已被禁用";
                break;
            case 500:
                rtn = "很抱歉，服务器遇到错误，无法完成请求";
                break;
            case 501:
                rtn = "很抱歉，服务器无法识别您请求的方法";
                break;
            default:
                break;
        }

        return rtn;
    }
}
