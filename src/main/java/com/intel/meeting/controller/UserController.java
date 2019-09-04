package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.MD5Utils;
import com.intel.meeting.vo.RtnIdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
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
    @Value("${USER_INIT_ROLE}")
    private String USER_INIT_ROLE;

    /**
     * 跳转到查询用户界面
     * @return
     */
    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(Model model){
        List<Role> roleList = roleService.findAllRoles();
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

    /**
     * 验证用户名和邮箱的重复性
     * @param username
     * @param email
     * @return
     */
    @PostMapping("/user/getcode")
    @ResponseBody
    public RtnIdInfo getVCode(String username,String email){
        String result = userService.getVCode(username,email);
        return new RtnIdInfo(result,0);
    }

    /**
     * 注册功能
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public RtnIdInfo register(User user,String code){
        Role role = null;
        role = roleService.findByRoleName(new String(USER_INIT_ROLE));
        user.setRole(role);

        String result = userService.register(user,code);
        return new RtnIdInfo(result,0);
    }

    /**
     * 登陆功能
     * @param usernameoremail
     * @param password
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public RtnIdInfo login(String usernameoremail,String password){
        String result = userService.login(usernameoremail, password);
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
        Role role=roleService.findByRoleName(roleName);
        user.setRole(role);
        System.out.println("user:"+user);
       String result = userService.saveUser(user);

        return new RtnIdInfo("save", 1);
    }
}
