package com.intel.meeting.service.impl;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.repository.ReserveMeetingRepository;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.vo.MainMr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
     * 根据id查询预定
     *
     * @param reserveId
     * @return
     */
    @Override
    public ReserveMeeting findOneById(Integer reserveId) {
        return rmRepository.findOne(reserveId);
    }

    /**
     * 根据id删除预定
     *
     * @param reserveId
     */
    @Override
    public void delReserveMeetingById(Integer reserveId) {
        rmRepository.delete(reserveId);
    }

    @Override
    public List<ReserveMeeting> finAllReserveMeeting() {
        return rmRepository.findAll();
    }

    /**
     * 保存预定
     *
     * @param reserveMeeting
     * @return
     */
    @Override
    public MainMr save(ReserveMeeting reserveMeeting) {

        Set<ReserveMeeting> reserveSet = reserveMeeting.getMeetingRoom().getReserveSet();
        String reserveTime = "";
        //判断预定的会议室时间是否冲突
        if (reserveSet.size() != 0) {
            for (ReserveMeeting meeting : reserveSet) {
                reserveTime += meeting.getStartTime()
                        + "--"
                        + meeting.getEndTime().split(" ")[1]
                        + "<br/>";
                long start1 = DateUtils.stringToTime(meeting.getStartTime());
                long end1 = DateUtils.stringToTime(meeting.getEndTime());
                long start2 = DateUtils.stringToTime(reserveMeeting.getStartTime());
                long end2 = DateUtils.stringToTime(reserveMeeting.getEndTime());
                //时间不冲突
                if (end2 <= start1 || start2 >= end1) {
                    continue;
                } else {
                    return new MainMr("timeError");
                }
            }
            //时间不冲突，添加预定
            rmRepository.save(reserveMeeting);
            reserveTime += reserveMeeting.getStartTime()
                    + "--"
                    + reserveMeeting.getEndTime().split(" ")[1]
                    + "<br/>";
            MeetingRoom meetingRoom = reserveMeeting.getMeetingRoom();
            return new MainMr(meetingRoom.getMeetingId(),
                    meetingRoom.getMeetingName(),
                    meetingRoom.getContainNum(),
                    "存在预约",
                    reserveTime,
                    "reserve");
        } else {
            //此会议室没有预约，直接保存
            rmRepository.save(reserveMeeting);
        }
        reserveTime += reserveMeeting.getStartTime()
                + "--"
                + reserveMeeting.getEndTime().split(" ")[1]
                + "<br/>";
        MeetingRoom meetingRoom = reserveMeeting.getMeetingRoom();
        return new MainMr(meetingRoom.getMeetingId(),
                meetingRoom.getMeetingName(),
                meetingRoom.getContainNum(),
                "存在预约",
                reserveTime,
                "reserve");
    }

    /**
     * 取消预定
     * 同时对比数据库中预定时间和当前时间
     * 再次校验是否正常取消
     * 尚未实现删除
     *
     * @param reserveId
     * @return
     */
    @Override
    public String cancelReserveMeeting(Integer reserveId) {
        ReserveMeeting reserveMeeting = rmRepository.getOne(reserveId);
        String startString = reserveMeeting.getStartTime();
        long nowTime = new Date().getTime();
        long startTime = DateUtils.stringToTime(startString);
        if (nowTime > startTime) {
            return "started";
        }
        ReserveMeeting one = rmRepository.findOne(reserveId);
        //使用状态改为2
        one.setUsageStatus(2);
        rmRepository.save(one);
        return "success";
    }

    /**
     * 会议室预定  --签到
     *
     * @param reserveId
     * @return
     */
    @Override
    public String signReserveMeeting(Integer reserveId) {
        ReserveMeeting reserveMeeting = rmRepository.getOne(reserveId);
        String startString = reserveMeeting.getStartTime();
        Date nowDate = new Date();
        long nowTime = nowDate.getTime();
        long startTime = DateUtils.stringToTime(startString);
        if (nowTime - startTime > 300000 || nowTime - startTime < 0) {
            return "exceed";
        } else {
            String signTime = DateUtils.sdf.format(nowDate);
            reserveMeeting.setSignTime(signTime);
            rmRepository.save(reserveMeeting);
            return "success";
        }
    }

    /**
     * 提前结束使用
     * 尚未实现删除
     *
     * @param reserveId
     * @return
     */
    @Override
    public String overReserveMeeting(Integer reserveId) {
        ReserveMeeting reserveMeeting = rmRepository.getOne(reserveId);
        System.out.println("reserveMeeting = " + reserveMeeting);
        long startTime = DateUtils.stringToTime(reserveMeeting.getStartTime());
        long endTime = DateUtils.stringToTime(reserveMeeting.getEndTime());
        long nowTime = new Date().getTime();
        if (nowTime - startTime > 300000 && nowTime < endTime) {
            return "success";
        } else {
            return "exceed";
        }
    }

}
