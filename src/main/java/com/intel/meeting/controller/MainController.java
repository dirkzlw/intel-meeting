package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.UserAuthService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.utils.HttpUtils;
import com.intel.meeting.utils.MainMrUtils;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.vo.MRPage;
import com.intel.meeting.vo.MainMr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-08-30 21:57
 */
@Controller
public class MainController {

    @Autowired
    private MeetingRoomService mrService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    /**
     * 重定向至：/index
     *
     * @return 重定向的url
     */
    @GetMapping("/")
    public String toI() {
        return "redirect:/index";
    }

    /**
     * 请求主页
     *
     * @param model   模型
     * @param page    当前页数  页面正常显示数字，后台数据查询从0开始第一页
     * @param request 请求
     * @return 指向主页
     */
    @GetMapping("/index")
    public String toIndex(Model model,
                          @RequestParam(required = false) Integer page,
                          HttpServletRequest request) {
        if (page == null) {
            page = 0;
        }
        Page<MeetingRoom> mrPage = mrService.findMeetingRoomByPage(page, 6);
        List<MainMr> mainMrList = MainMrUtils.mrListToMainMrList(mrPage.getContent());
        MRPage mrPageInfo = new MRPage(mainMrList,
                page + 1,
                mrPage.getTotalPages(),
                (int) mrPage.getTotalElements(),
                2);
        model.addAttribute("mainMrPage", mrPageInfo);
        SessionUtils.setUserIndex(model, request);
        return "index/index";
    }

    /**
     * 主页查询
     *
     * @param model       模型
     * @param request     请求
     * @param meetingName 会议室名称
     * @param searchDay   查询的日期   2019-09-11
     * @param searchStart 当前日期开始时间    08:00
     * @param searchEnd   当前日期结束时间    09:00
     * @return 返回主页
     */
    @GetMapping("/index/search")
    public String indexSearch(Model model,
                              HttpServletRequest request,
                              String meetingName,
                              String searchDay,
                              String searchStart,
                              String searchEnd) {

        List<MeetingRoom> mrList = mrService.findMeetingRoomLikeName("%" + meetingName + "%");
        List<MainMr> mainList;

        if (!"".equals(searchDay) && !"".equals(searchStart) && !"".equals(searchEnd)) {
            long searchStartL = DateUtils.stringToTime(searchDay + " " + searchStart);
            long searchEndL = DateUtils.stringToTime(searchDay + " " + searchEnd);
            mainList = MainMrUtils.indexSearch(mrList, searchStartL, searchEndL, searchDay);
        } else {
            mainList = MainMrUtils.mrListToMainMrList(mrList);
        }

        MRPage mrPageInfo = new MRPage(mainList,
                1,
                1,
                mainList.size(),
                1);
        model.addAttribute("mainMrPage", mrPageInfo);
        SessionUtils.setUserIndex(model, request);
        return "index/index";
    }

    /**
     * 跳转至控制中心主页
     *
     * @param model   模型
     * @param request 请求
     * @return 控制中心主页
     */
    @GetMapping("/to/control")
    public String toControl(Model model,
                            HttpServletRequest request) {
        //查询各状态会议室的数量
        int freeNum = mrService.countByEnableStatus("空闲");
        int downNum = mrService.countByEnableStatus("故障");
        model.addAttribute("freeNum", freeNum);
        model.addAttribute("downNum", downNum);

        SessionUtils.setUserIndex(model, request);
        return "control/index";
    }

    /**
     * 跳转至用户管理主页
     *
     * @param model   模型
     * @param request 主页
     * @return 用户管理主页
     */
    @GetMapping("/to/usermgn")
    public String toUsermgn(Model model,
                            HttpServletRequest request) {

        //统计注册人数
        int rn = userService.countRegistNum();
        model.addAttribute("registNum", rn);

        // 统计认证各状态的人数
        Integer n1 = userAuthService.countUserAuthStatus(1);
        Integer n2 = userAuthService.countUserAuthStatus(2);
        Integer n3 = userAuthService.countUserAuthStatus(3);
        model.addAttribute("authStatus1", n1);
        model.addAttribute("authStatus2", n2);
        model.addAttribute("authStatus3", n3);
        SessionUtils.setUserIndex(model, request);
        return "usermgn/index";
    }

    /**
     * 异常处理
     *
     * @param model     模型
     * @param errorCode http状态码
     * @return 执行错误处理页面
     */
    @GetMapping("/to/error")
    public String toError(Model model, Integer errorCode) {
        String errorMsg = HttpUtils.getMessageByCode(errorCode);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMsg", errorMsg);
        return "index/error";
    }

}
