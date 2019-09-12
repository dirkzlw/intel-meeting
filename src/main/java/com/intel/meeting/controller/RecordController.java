package com.intel.meeting.controller;

import com.intel.meeting.po.Record;
import com.intel.meeting.po.es.EsRecord;
import com.intel.meeting.service.RecordService;
import com.intel.meeting.service.es.EsRecordService;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.vo.GraphInfo;
import com.intel.meeting.vo.MRPage;
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
 * @create 2019-09-06 16:28
 */
@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private EsRecordService esRecordService;

    /**
     * 跳转至控制中心--使用统计
     *
     * @param model   模型
     * @param request 请求
     * @return 控制中心--使用统计
     */
    @GetMapping("/to/control/graph")
    public String toControlGraph(Model model,
                                 HttpServletRequest request) {

        GraphInfo graphInfo = recordService.getGraphInfo();
        model.addAttribute("graphInfo", graphInfo);
        SessionUtils.setUserIndex(model, request);

        return "control/graph";
    }

    /**
     * 跳转至控制中心--使用记录
     *
     * @param model   模型
     * @param request 请求
     * @param page    当前页
     * @return 控制中心-使用记录
     */
    @GetMapping("/to/control/record")
    public String toControlRecord(Model model,
                                  HttpServletRequest request,
                                  @RequestParam(required = false) Integer page) {
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

        SessionUtils.setUserIndex(model, request);

        return "control/record";
    }

    /**
     * 查询会议室
     *
     * @param nameVal 关键字
     * @param model   模型
     * @return 使用记录查询结果
     */
    @GetMapping("/to/control/record/search")
    private String searchRecord(String nameVal, Model model,
                                HttpServletRequest request) {
        System.out.println(nameVal);
        List<EsRecord> esrecordList = esRecordService.findDistinctByRealnameContainingOrMeetingAddressContaining(nameVal);
        MRPage mrPage = new MRPage(esrecordList,
                1,
                1,
                esrecordList.size(),
                1);
        model.addAttribute("recordPage", mrPage);
        SessionUtils.setUserIndex(model, request);
        return "control/record";
    }
}
