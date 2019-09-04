package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.po.User;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.vo.MainMr;
import com.intel.meeting.vo.RtnIdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 预定会议室
 *
 * @author Ranger
 * @create 2019-09-03 20:53
 */
@Controller
public class ReserveMeetingController {

    @Autowired
    private MeetingRoomService mrService;

    @Autowired
    private ReserveMeetingService rmService;

    /**
     * 预定会议室
     *
     * @param meetingId
     * @param reserveDay
     * @param startTime
     * @param endTime
     * @param request
     * @return
     */
    @PostMapping("/meeting/reserve")
    @ResponseBody
    public MainMr reserveMeeting(Integer meetingId,
                                 String reserveDay,
                                 String startTime,
                                 String endTime,
                                 HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("sessionUser");
        MeetingRoom meetingRoom = mrService.findMrById(meetingId);
        ReserveMeeting reserveMeeting = new ReserveMeeting(user,
                meetingRoom,
                reserveDay + " " + startTime,
                reserveDay + " " + endTime,
                1,
                "0000-00-00 00:00");

        return rmService.save(reserveMeeting);
    }
}
