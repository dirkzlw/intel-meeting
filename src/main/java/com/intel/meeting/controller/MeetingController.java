package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.vo.MRRtnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-01 20:50
 */
@Controller
public class MeetingController {

    //注入meetingroomservicefuwu
    @Autowired
    private MeetingRoomService mrService;

    /**
     * 跳转到会议室管理界面
     * 同时分页展示所有会议室
     * @return
     */
    @GetMapping("/to/control/meeting-manage")
    public String toMeetingManage(Model model){
        List<MeetingRoom> mrList = mrService.findAllMeetingRoom();
        model.addAttribute("mrList", mrList);
        return "control/meeting-manage";
    }

    /**
     * 添加会议室
     * @return
     */
    @PostMapping("/control/meeting/save")
    @ResponseBody
    public MRRtnInfo addMeeting(MeetingRoom meetingRoom){

        System.out.println("meetingRoom = " + meetingRoom);

        String result = mrService.saveMeetingRoom(meetingRoom);

        return new MRRtnInfo(result,meetingRoom.getMeetingId());
    }

    /**
     * 删除会议室
     * @return
     */
    @PostMapping("/control/meeting/del")
    @ResponseBody
    public String delMeeting(Integer meetingId){

        String result = mrService.delMeetingRoom(meetingId);

        return result;
    }

}
