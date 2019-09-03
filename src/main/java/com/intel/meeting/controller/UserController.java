package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.vo.RtnIdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private UserService userService;

    @Autowired
    private UserService userService;
    /**
     * 跳转到查询用户界面
     * @return
     */
    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(Model model){
        List<Role> roleList = roleService.findAllRoles();
        System.out.println("roleList = " + roleList);
        model.addAttribute("roleList", roleList);
        return "usermgn/user-manage";
    }

    /**
     * 跳转到登陆界面
     * @return
     */
    @GetMapping("/to/user/login")
    public String toUserLogin(){
        return "user/login";
    }

    @PostMapping("/user/getcode")
    @ResponseBody
    public RtnIdInfo getVCode(String username,String email){
        System.out.println("username = " + username);
        System.out.println("email = " + email);
        String result = userService.getVCode(username,email);
        return new RtnIdInfo(result,0);
    }





    /**
     * 管理员添加用户功能
     * @param user
     * @return
     */
    @PostMapping("/usermgn/user/save")
    @ResponseBody
    public RtnIdInfo saveUser(User user,String roleName){
//        System.out.println(roleName);
        Role role=roleService.findByRoleName(roleName);
        user.setRole(role);
        System.out.println("user:"+user);
       String result = userService.savaUser(user);

//        return new RtnIdInfo(result,user.getUserId());
//        System.out.println("user = " + user);

        return new RtnIdInfo("save", 1);
    }
}
