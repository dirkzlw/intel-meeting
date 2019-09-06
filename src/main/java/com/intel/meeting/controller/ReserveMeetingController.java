package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.po.User;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.utils.ReserveMeetingUtiles;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.utils.UserUtils;
import com.intel.meeting.vo.MainMr;
import com.intel.meeting.vo.ReserveMeetingInfo;
import com.intel.meeting.vo.RtnIdInfo;
import com.intel.meeting.vo.SessionUser;
import com.intel.meeting.vo.UserIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private UserService userService;
    @Autowired
    private ReserveMeetingService rmService;

    /**
     * 前往用户预定中心
     * @return
     */
    @GetMapping("/to/reserve/center")
    public String toReserveCenter(Model model,
                                  HttpServletRequest request){
        //从session中获取用户
        SessionUser userIndex = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
        //获取用户的预定列表
//        List<ReserveMeeting> rmList = rmService.findReserveByUserId(userIndex.getUserId());

        User user = userService.findUserById(userIndex.getUserId());
        Set<ReserveMeeting> reserveSet = user.getReserveSet();
        List<ReserveMeetingInfo> rmiList = ReserveMeetingUtiles.reserveSetToRMList(reserveSet);
        //根据预定顺序排序
        Collections.sort(rmiList);
        model.addAttribute("rmiList", rmiList);
        UserUtils.setUserIndex(model, request);

        return "user/reserve-center";
    }

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
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("sessionUser");
        User user = userService.findUserById(sessionUser.getUserId());
        MeetingRoom meetingRoom = mrService.findMrById(meetingId);
        ReserveMeeting reserveMeeting = new ReserveMeeting(user,
                meetingRoom,
                reserveDay + " " + startTime,
                reserveDay + " " + endTime,
                1,
                "0000-00-00 00:00");

        return rmService.save(reserveMeeting);
    }

    /**
     * 取消预定
     * @param reserveId
     * @return
     */
    @PostMapping("/meeting/reserve/cancel")
    @ResponseBody
    public String cancelReserveMeeting(Integer reserveId){

        return rmService.delReserveMeetingById(reserveId);
    }

    /**
     * 签到
     * @param reserveId
     * @return
     */
    @PostMapping("/meeting/reserve/sign")
    @ResponseBody
    public String signReserveMeeting(Integer reserveId){

        return rmService.signReserveMeeting(reserveId);
    }

    @PostMapping("/meeting/reserve/over")
    @ResponseBody
    public String overReserveMeeting(Integer reserveId){

        String result =  rmService.overReserveMeeting(reserveId);

        //当成功结束会议时，

        return result;
    }
}
