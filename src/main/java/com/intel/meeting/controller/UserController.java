package com.intel.meeting.controller;

import com.intel.meeting.po.User;
import com.intel.meeting.vo.RtnIdInfo;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理关于用户的请求
 * @author Ranger
 * @create 2019-09-02 21:56
 */
@Controller
public class UserController {
    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(){
        return "usermgn/user-manage";
    }

    @PostMapping("/usermgn/user/save")
    @ResponseBody
    public RtnIdInfo saveUser(User user){

        System.out.println("user = " + user);

        return new RtnIdInfo("save", 1);
    }
}
