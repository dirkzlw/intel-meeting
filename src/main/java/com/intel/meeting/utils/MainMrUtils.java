package com.intel.meeting.utils;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.vo.MainMr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 转换主页显示的List
 * @author Ranger
 * @create 2019-09-03 20:00
 */
public class MainMrUtils {
    public static List<MainMr> mrListToMainMrList(List<MeetingRoom> mrList){
        List<MainMr> mainMrList = new ArrayList<>();
        for (MeetingRoom mr : mrList) {
            //判定预约状态和预约时间
            Set<ReserveMeeting> reserveSet = mr.getReserveSet();
            String reserveStatus;
            String reserveTime="";
            if(reserveSet.size()!=0){
                reserveStatus = "存在预定";
                for (ReserveMeeting reserveMeeting : reserveSet) {
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
