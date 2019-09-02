package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.es.EsMeetingRoom;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.es.EsMeetingRoomService;
import com.intel.meeting.utils.EmrUtils;
import com.intel.meeting.vo.MRPage;
import com.intel.meeting.vo.RtnIdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理关于会议室的请求
 *
 * @author Ranger
 * @create 2019-09-01 20:50
 */
@Controller
public class MeetingController {

    //注入meetingroomservicefuwu
    @Autowired
    private MeetingRoomService mrService;

    @Autowired
    private EsMeetingRoomService emrService;

    /**
     * 跳转到会议室管理界面
     * 同时分页展示所有会议室
     *
     * @return
     */
    @GetMapping("/to/control/meeting-manage")
    public String toMeetingManage(Model model,
                                  @RequestParam(required = false) Integer page) {
        if (page == null) {
            page = 0;
        }
        Page<MeetingRoom> mrPage = mrService.findMeetingRoomByPage(page, 10);
        MRPage mrPageInfo = new MRPage(mrPage.getContent(),
                page + 1,
                mrPage.getTotalPages(),
                (int) mrPage.getTotalElements(),
                2);
        model.addAttribute("mrPage", mrPageInfo);
        return "control/meeting-manage";
    }

    /**
     * 添加会议室
     *
     * @return
     */
    @PostMapping("/control/meeting/save")
    @ResponseBody
    public RtnIdInfo addMeeting(MeetingRoom meetingRoom) {

        String result = mrService.saveMeetingRoom(meetingRoom);

        //同步搜索库
        emrService.save(new EsMeetingRoom(meetingRoom.getMeetingId(),
                meetingRoom.getMeetingName(),
                meetingRoom.getContainNum(),
                meetingRoom.getEnableStatus()));

        return new RtnIdInfo(result, meetingRoom.getMeetingId());
    }

    /**
     * 删除会议室
     *
     * @return
     */
    @PostMapping("/control/meeting/del")
    @ResponseBody
    public String delMeeting(Integer meetingId) {

        String result = mrService.delMeetingRoom(meetingId);

        //同步es
        emrService.delEsMeetingRoomById(meetingId);

        return result;
    }

    /**
     * 查询会议室
     * @param mrkey 关键字
     * @param model
     * @return
     */
    @GetMapping("/to/control/meeting/search")
    private String searchMeeting(String mrkey,Model model){
        List<EsMeetingRoom> emrList = emrService.findEsMeetingRoomByMeetingName(mrkey);
        List<MeetingRoom> mrList = EmrUtils.emrListToMrList(emrList);
        MRPage mrPage = new MRPage(mrList,
                1,
                1,
                mrList.size(),
                1);
        System.out.println("mrPage.getMrList() = " + mrPage.getMrList());
        model.addAttribute("mrPage", mrPage);
        return "control/meeting-manage";
    }

}
