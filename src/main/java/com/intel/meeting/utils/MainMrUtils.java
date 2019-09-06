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
 * @author Ranger
 * @create 2019-09-03 20:00
 */
public class MainMrUtils {

    /**
     * 将会议室表、预定表整合
     * 展示在主页
     * @param mrList
     * @return
     */
    public static List<MainMr> mrListToMainMrList(List<MeetingRoom> mrList){
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
            String reserveTime="";
            if(reserveSet.size()!=0){
                reserveStatus = "存在预定";
                for (ReserveMeeting reserveMeeting : reserveList) {
                    reserveTime += reserveMeeting.getStartTime()
                            +"--"
                            + reserveMeeting.getEndTime().split(" ")[1]
                            +"<br/>";
                }
            }else {
                String enableStatus = mr.getEnableStatus();
                if("空闲".equals(enableStatus)){
                    reserveStatus = "随时预约";
                }else {
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
}
