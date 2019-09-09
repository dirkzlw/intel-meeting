package com.intel.meeting.utils;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.vo.MainMr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 转换主页显示的List
 *
 * @author Ranger
 * @create 2019-09-03 20:00
 */
public class MainMrUtils {

    /**
     * 将会议室表、预定表整合
     * 展示在主页
     *
     * @param mrList
     * @return
     */
    public static List<MainMr> mrListToMainMrList(List<MeetingRoom> mrList) {
        List<MainMr> mainMrList = new ArrayList<>();
        for (MeetingRoom mr : mrList) {
            //判定预约状态和预约时间
            Set<ReserveMeeting> reserveSet = mr.getReserveSet();
            //将Set转换为List,进而排序
            List<ReserveMeeting> reserveList = new ArrayList<>();
            for (ReserveMeeting reserveMeeting : reserveSet) {
                reserveList.add(reserveMeeting);
            }
            Collections.sort(reserveList);
            String reserveStatus;
            String reserveTime = "";
            if (reserveSet.size() != 0) {
                reserveStatus = "存在预定";
                for (ReserveMeeting reserveMeeting : reserveList) {
                    reserveTime += reserveMeeting.getStartTime()
                            + "--"
                            + reserveMeeting.getEndTime().split(" ")[1]
                            + "<br/>";
                }
            } else {
                String enableStatus = mr.getEnableStatus();
                if ("空闲".equals(enableStatus)) {
                    reserveStatus = "随时预约";
                } else {
                    reserveStatus = "会议室故障";
                }
            }
            MainMr mainMr = new MainMr(mr.getMeetingId(),
                    mr.getMeetingName(),
                    mr.getContainNum(),
                    reserveStatus,
                    reserveTime,
                    null);
            mainMrList.add(mainMr);
        }
        return mainMrList;
    }

    /**
     * 主页根据时间查询
     *
     * @param mrList
     * @param searchStartL
     * @param searchEndL
     * @param searchDay
     * @return
     */
    public static List<MainMr> indexSearch(List<MeetingRoom> mrList, long searchStartL, long searchEndL, String searchDay) {
        List<MainMr> mainMrList = new ArrayList<>();

        for (MeetingRoom mr : mrList) {
            boolean flag = true;
            //判定预约状态和预约时间
            Set<ReserveMeeting> reserveSet = mr.getReserveSet();
            //将Set转换为List,进而排序
            List<ReserveMeeting> reserveList = new ArrayList<>();
            for (ReserveMeeting reserveMeeting : reserveSet) {
                reserveList.add(reserveMeeting);
            }
            Collections.sort(reserveList);
            String reserveStatus = "";
            String reserveTime = "";
            if (reserveSet.size() != 0) {
                reserveStatus = "存在预定";
                for (ReserveMeeting reserveMeeting : reserveList) {
                    //遍历预定列表，确定是否时间冲突
                    long start2 = DateUtils.stringToTime(reserveMeeting.getStartTime());
                    long end2 = DateUtils.stringToTime(reserveMeeting.getEndTime());
                    if (end2 <= searchStartL || start2 >= searchEndL) {
                        //时间不冲突
//                        if (reserveMeeting.getStartTime().indexOf(searchDay) != -1) {
//                            reserveTime +="<span style=\"color: #C24343\">"
//                                    + reserveMeeting.getStartTime()
//                                    + "--"
//                                    + reserveMeeting.getEndTime().split(" ")[1]
//                                    +"</span>"
//                                    + "<br/>";
//                            continue;
//                        }
                        if (reserveMeeting.getStartTime().indexOf(searchDay) != -1) {
                            reserveTime +="<span style=\"color: #C24343\">"
                                    + reserveMeeting.getStartTime()
                                    + "--"
                                    + reserveMeeting.getEndTime().split(" ")[1]
                                    +"</span>"
                                    + "<br/>";
                        }else {
                            reserveTime += reserveMeeting.getStartTime()
                                    + "--"
                                    + reserveMeeting.getEndTime().split(" ")[1]
                                    + "<br/>";
                        }
                    } else {
                        flag = false;
                        break;
                    }
                }
            } else {
                if ("".equals(reserveTime)) {
                    reserveStatus = "随时预约";
                }else {
                    reserveStatus = "存在预定";
                }
            }
            if (flag) {
                MainMr mainMr = new MainMr(mr.getMeetingId(),
                        mr.getMeetingName(),
                        mr.getContainNum(),
                        reserveStatus,
                        reserveTime,
                        null);
                mainMrList.add(mainMr);
            }
        }
        return mainMrList;
    }
}
