package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.vo.RtnIdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理关于用户的请求
 * @author Ranger
 * @create 2019-09-02 21:56
 */
@Controller
public class UserController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(Model model){
        List<Role> roleList = roleService.findAllRoles();
        System.out.println("roleList = " + roleList);
        model.addAttribute("roleList", roleList);
        return "usermgn/user-manage";
    }

    @PostMapping("/usermgn/user/save")
    @ResponseBody
    public RtnIdInfo saveUser(User user){

        System.out.println("user = " + user);

        return new RtnIdInfo("save", 1);
    }
}
