package com.intel.meeting.utils;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.vo.ReserveMeetingInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Ranger
 * @create 2019-09-05 16:11
 */
public class ReserveMeetingUtiles {

    /**
     * 通过中间表（预定记录表），整合用户、会议室、预定三张信息
     * 展示在用户预定中心
     *
     * @param reserveSet
     * @return
     */
    public static List<ReserveMeetingInfo> reserveSetToRMList(Set<ReserveMeeting> reserveSet) {
        List<ReserveMeetingInfo> rmiList = new ArrayList<>();
        for (ReserveMeeting rm : reserveSet) {
            MeetingRoom mr = rm.getMeetingRoom();
            String[] split = rm.getStartTime().split(" ");
            String reserveDay = split[0];
            String startTime = split[1];
            String endTime = rm.getEndTime().split(" ")[1];
            String signDay = rm.getSignTime().split(" ")[0];
            Integer signStatus;
            if ("0000-00-00".equals(signDay)) {
                signStatus = 2;
            } else {
                signStatus = 1;
            }
            long endTime2 = DateUtils.stringToTime(rm.getEndTime());
            long nowTime = new Date().getTime();
            Integer endStatus;
            if (nowTime - endTime2 > 0) {
                endStatus = 1;
            } else {
                endStatus = 2;
            }
            rmiList.add(
                    new ReserveMeetingInfo(rm.getReserveId(),
                            rm.getUser().getUserId(),
                            mr.getMeetingId(),
                            mr.getMeetingName(),
                            mr.getContainNum(),
                            reserveDay,
                            startTime,
                            endTime,
                            signStatus,
                            endStatus)
            );
        }
        return rmiList;
    }

}
