package com.intel.meeting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ranger
 * @create 2019-09-06 16:28
 */
@Controller
public class RecordController {

    /**
     * 跳转至统计图表界面
     * @return
     */
    @GetMapping("/to/control/graph")
    public String toControlGraph(){
        return "control/graph";
    }

    /**
     * 跳转至统计记录界面
     * @return
     */
    @GetMapping("/to/control/record")
    public String toControlRecord(){
        return "control/record";
    }
}
