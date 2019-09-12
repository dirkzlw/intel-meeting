package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.es.EsMeetingRoom;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.es.EsMeetingRoomService;
import com.intel.meeting.utils.SessionUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理关于会议室的请求
 *
 * @author Intel-Meeting
 * @create 2019-09-01 20:50
 */
@Controller
public class MeetingController {

    //注入MeetingroomService服务
    @Autowired
    private MeetingRoomService mrService;

    @Autowired
    private EsMeetingRoomService emrService;

    /**
     * 跳转至控制中心--会议室管理界面
     *
     * @param model   模型
     * @param page    当前页
     * @param request 请求
     * @return 控制中心--会议室管理界面
     */
    @GetMapping("/to/control/meeting-manage")
    public String toMeetingManage(Model model,
                                  @RequestParam(required = false) Integer page,
                                  HttpServletRequest request) {
        if (page == null) {
            page = 0;
        }
        Page<MeetingRoom> mrPage = mrService.findMeetingRoomByPage(page, 9);
        MRPage mrPageInfo = new MRPage(mrPage.getContent(),
                page + 1,
                mrPage.getTotalPages(),
                (int) mrPage.getTotalElements(),
                2);
        model.addAttribute("mrPage", mrPageInfo);
        SessionUtils.setUserIndex(model, request);
        return "control/meeting-manage";
    }

    /**
     * 保存会议室
     * 当添加/保存会议室调用
     *
     * @param meetingRoom 会议室名称
     * @return 保存结果
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
     * @param meetingId 会议室id
     * @return 删除结果
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
     *
     * @param mrkey 关键字
     * @param model 模型
     * @return 查询结果
     */
    @GetMapping("/to/control/meeting/search")
    private String searchMeeting(String mrkey, Model model,
                                 HttpServletRequest request) {
        List<EsMeetingRoom> emrList = emrService.findEsMeetingRoomByMeetingName(mrkey);
        MRPage mrPage = new MRPage(emrList,
                1,
                1,
                emrList.size(),
                1);
        model.addAttribute("mrPage", mrPage);
        SessionUtils.setUserIndex(model, request);
        return "control/meeting-manage";
    }

}
