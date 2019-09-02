package com.intel.meeting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ranger
 * @create 2019-08-30 21:57
 */
@Controller
public class MainController {

    /**
     * 接收Get请求，URL：http://www.intel.com
     * 跳转至页面：index/index.html
     * @return
     */
    @GetMapping("/")
    public String toI(){
        return "index/index";
    }

    /**
     * 接收URL:http://www.intel.com/to/control
     * 跳转至页面control/index.html
     * @return
     */
    @GetMapping("/to/control")
    public String toControl(){
        return "control/index";
    }

    /**
     * 接收URL:http://www.intel.com/to/usermgn
     * 跳转至页面usermgn/index.html
     * @return
     */
    @GetMapping("/to/usermgn")
    public String toUsermgn(){
        return "usermgn/index";
    }

}
