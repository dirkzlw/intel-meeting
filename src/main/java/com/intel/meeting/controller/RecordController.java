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
        UserUtils.setUserIndex(model, request);
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
