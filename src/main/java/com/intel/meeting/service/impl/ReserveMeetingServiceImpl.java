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
import java.util.List;
import java.util.Set;

import static org.aspectj.bridge.Version.getTime;

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

    /**
     * 根据id删除预定 --取消预定
     * 同时对比数据库中预定时间和当前时间
     * 再次校验是否正常取消
     * 尚未实现删除
     * @param reserveId
     * @return
     */
    @Override
    public String delReserveMeetingById(Integer reserveId) {
        ReserveMeeting reserveMeeting = rmRepository.getOne(reserveId);
        String startString = reserveMeeting.getStartTime();
        long nowTime = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long startTime = DateUtils.stringToDate(sdf, startString).getTime();
        if(nowTime - startTime >0 ){
            return "started";
        }
        return "success";
    }

    /**
     * 会议室预定  --签到
     * @param reserveId
     * @return
     */
    @Override
    public String signReserveMeeting(Integer reserveId) {
        ReserveMeeting reserveMeeting = rmRepository.getOne(reserveId);
        String startString = reserveMeeting.getStartTime();
        Date nowDate = new Date();
        long nowTime = nowDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long startTime = DateUtils.stringToDate(sdf, startString).getTime();
        if(nowTime - startTime >300000 || nowTime -startTime <0  ){
            return "exceed";
        }else {
            String signTime = sdf.format(nowDate);
            reserveMeeting.setSignTime(signTime);
            rmRepository.save(reserveMeeting);
            return "success";
        }
    }

    /**
     * 提前结束使用
     * 尚未实现删除
     * @param reserveId
     * @return
     */
    @Override
    public String overReserveMeeting(Integer reserveId) {
        ReserveMeeting reserveMeeting = rmRepository.getOne(reserveId);
        System.out.println("reserveMeeting = " + reserveMeeting);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long startTime = DateUtils.stringToDate(sdf, reserveMeeting.getStartTime()).getTime();
        long endTime = DateUtils.stringToDate(sdf, reserveMeeting.getEndTime()).getTime();
        long nowTime = new Date().getTime();
        if (nowTime - startTime > 300000 && nowTime < endTime){
            return "success";
        }else {
            return "exceed";
        }
    }
}
