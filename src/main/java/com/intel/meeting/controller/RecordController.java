package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.Record;
import com.intel.meeting.service.RecordService;
import com.intel.meeting.utils.UserUtils;
import com.intel.meeting.vo.MRPage;
import com.intel.meeting.vo.UserIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ranger
 * @create 2019-09-06 16:28
 */
@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;


    /**
     * 跳转至统计图表界面
     * @return
     */
    @GetMapping("/to/control/graph")
    public String toControlGraph(Model model,
                                 HttpServletRequest request){

        //获取使用率
        String useRate = recordService.getUaeRage();
        model.addAttribute("useRate", useRate);
        //获取签到率
        String signRate = recordService.getSignRate();
        model.addAttribute("signRate", signRate);
        UserUtils.setUserIndex(model, request);
        //获取周1--7的预约量
        long week1 = recordService.getReserveNumOfWeek(1);
        System.out.println("week1 = " + week1);
        long week2 = recordService.getReserveNumOfWeek(2);
        long week3 = recordService.getReserveNumOfWeek(3);
        long week4 = recordService.getReserveNumOfWeek(4);
        long week5 = recordService.getReserveNumOfWeek(5);
        long week6 = recordService.getReserveNumOfWeek(6);
        long week0 = recordService.getReserveNumOfWeek(0);
        model.addAttribute("week1", week1);
        model.addAttribute("week2", week2);
        model.addAttribute("week3", week3);
        model.addAttribute("week4", week4);
        model.addAttribute("week5", week5);
        model.addAttribute("week6", week6);
        model.addAttribute("week0", week0);

        return "control/graph";
    }

    /**
     * 跳转至统计记录界面
     * @return
     */
    @GetMapping("/to/control/record")
    public String toControlRecord(Model model,
                                  HttpServletRequest request,
                                  @RequestParam(required = false) Integer page){
        //分页查询所有记录
        if (page == null) {
            page = 0;
        }
        Page<Record> recordPage = recordService.findRecordByPage(page, 9);
        MRPage recordPageInfo = new MRPage(recordPage.getContent(),
                page + 1,
                recordPage.getTotalPages(),
                (int) recordPage.getTotalElements(),
                2);
        model.addAttribute("recordPage", recordPageInfo);

        UserUtils.setUserIndex(model, request);

        return "control/record";
    }
}
