package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.po.User;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.utils.DateUtils;
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
 * @author Ranger
 * @create 2019-09-03 20:53
 */
@Controller
public class ReserveMeetingController {

    @Autowired
    private MeetingRoomService mrService;

    @Autowired
    private ReserveMeetingService rmService;

    @PostMapping("/meeting/reserve")
    @ResponseBody
    public RtnIdInfo reserveMeeting(Integer meetingId,
                                    String reserveDay,
                                    String startTime,
                                    String endTime,
                                    HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("sessionUser");
        if(user == null){
            user = new User(null, null, null, null, null, null, null, null, null, null);
            user.setUserId(1);
        }
        MeetingRoom meetingRoom = mrService.findMrById(meetingId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        ReserveMeeting reserveMeeting = new ReserveMeeting(user,
                meetingRoom,
                DateUtils.dateTimeToDate(sdf, reserveDay, startTime),
                DateUtils.dateTimeToDate(sdf, reserveDay, endTime),
                1,
                DateUtils.dateTimeToDate(sdf, "1900-01-01", "00:00"));

        String rtn = rmService.save(reserveMeeting);

        return new RtnIdInfo(rtn, 10);
    }
}
