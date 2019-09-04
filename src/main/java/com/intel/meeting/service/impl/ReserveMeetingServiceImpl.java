package com.intel.meeting.service.impl;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.repository.ReserveMeetingRepository;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.vo.MainMr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author ranger
 * @create 2019-09 -03-11:20
 */
@Service
public class ReserveMeetingServiceImpl implements ReserveMeetingService {

    @Autowired
    private ReserveMeetingRepository rmRepository;

    /**
     * 保存预定
     * 没有判定冲突
     *
     * @param reserveMeeting
     * @return
     */
    @Override
    public MainMr save(ReserveMeeting reserveMeeting) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Set<ReserveMeeting> reserveSet = reserveMeeting.getMeetingRoom().getReserveSet();
        String reserveTime = "";
        //判断预定的会议室时间是否冲突
        if (reserveSet.size() != 0) {
            for (ReserveMeeting meeting : reserveSet) {
                reserveTime +=meeting.getStartTime()
                        +"--"
                        + meeting.getEndTime().split(" ")[1]
                        +"<br/>";
                Long start1 = DateUtils.stringToDate(sdf, meeting.getStartTime()).getTime();
                Long end1 = DateUtils.stringToDate(sdf, meeting.getEndTime()).getTime();
                Long start2 = DateUtils.stringToDate(sdf, reserveMeeting.getStartTime()).getTime();
                Long end2 = DateUtils.stringToDate(sdf, reserveMeeting.getEndTime()).getTime();

                if (end2 <= start1 || start2 >= end1) {
                    rmRepository.save(reserveMeeting);
                    reserveTime+=reserveMeeting.getStartTime()
                            +"--"
                            + reserveMeeting.getEndTime().split(" ")[1]
                            +"<br/>";
                    MeetingRoom meetingRoom = reserveMeeting.getMeetingRoom();
                    return new MainMr(meetingRoom.getMeetingId(),
                            meetingRoom.getMeetingName(),
                            meetingRoom.getContainNum(),
                            "存在预约",
                            reserveTime,
                            "reserve");
                }else {
                    return new MainMr("timeError");
                }
            }
        }else {
            //此会议室没有预约，直接保存
            rmRepository.save(reserveMeeting);
        }
        reserveTime+=reserveMeeting.getStartTime()
                +"--"
                + reserveMeeting.getEndTime().split(" ")[1]
                +"<br/>";
        MeetingRoom meetingRoom = reserveMeeting.getMeetingRoom();
        return new MainMr(meetingRoom.getMeetingId(),
                meetingRoom.getMeetingName(),
                meetingRoom.getContainNum(),
                "存在预约",
                reserveTime,
                "reserve");
    }
}
