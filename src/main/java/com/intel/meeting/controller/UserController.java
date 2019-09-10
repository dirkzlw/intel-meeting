package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsUser;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.service.es.EsUserService;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.utils.UserUtils;
import com.intel.meeting.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理关于用户的请求
 *
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
    private EsUserService esUserService;

    //用户初始角色
    @Value("${USER_INIT_ROLE}")
    private String USER_INIT_ROLE;
    @Value("123456")
    private String USER_INIT_PASSWORD;
    @Value("http://39.107.249.220/group1/M00/00/00/rBgo6l1wgf6ACAM5AAA5pzR1Lu8411.jpg")
    private String USER_INIT_HEAD_URL;
    /**
     * 跳转到用户管理主页
     *
     * @return
     */
    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(Model model,
                               @RequestParam(required = false) Integer page,
                               HttpServletRequest request) {
        List<Role> roleList = roleService.findAllRoles();
        if (page == null) {
            page = 0;
        }
        Page<User> userPage = userService.findUserByPage(page, 10);
        List<User> userList = userPage.getContent();
        List<UserInfo> userInfoList = new ArrayList<>();
        for (User user : userList) {
            UserInfo userInfo = new UserInfo(user.getUsername(),user.getEmail(), user.getRole().getRoleName());
            userInfo.setUserId(user.getUserId());
            userInfoList.add(userInfo);
        }
        MRPage userPageInfo = new MRPage(userInfoList,
                page + 1,
                userPage.getTotalPages(),
                (int) userPage.getTotalElements(),
                2);
        model.addAttribute("userPage", userPageInfo);
        model.addAttribute("roleList", roleList);
        UserUtils.setUserIndex(model, request);
        return "usermgn/user-manage";
    }

    @GetMapping("/to/user/msg")
    public String toUserMsg(Model model,
                            HttpServletRequest request){

        SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
        User user = userService.findUserById(sessionUser.getUserId());
        model.addAttribute("umsg",user);

        UserUtils.setUserIndex(model,request);

        return "user/user-msg";
    }

    /**
     * 跳转到登陆界面
     *
     * @return
     */
    @GetMapping("/to/user/login")
    public String toUserLogin() {
        return "user/login";
    }

    /**
     * 验证用户名和邮箱的重复性
     *
     * @param username
     * @param email
     * @return
     */
    @PostMapping("/user/getcode")
    @ResponseBody
    public String getVCode(String username, String email) {
        String result = userService.getVCode(username, email);
        return result;
    }

    /**
     * 重置密码
     * @param email
     * @return
     */
    @PostMapping("/user/resetpwd")
    @ResponseBody
    public  String reSetPwd(String email){
        return "success";
    }

    /**
     * 注册功能
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public String register(User user, String code,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        Role role = null;
        role = roleService.findByRoleName(new String(USER_INIT_ROLE));
        user.setRole(role);
        String result = userService.register(user, code);
        if ("success".equals(result)) {
            //将User转换为SessionUser
            SessionUser sessionUser = userToSessionUser(user);
            SessionUtils.saveObjectToSession(request, response, sessionUser, "sessionUser");
        }
        //同步搜索库
        esUserService.save(new EsUser(user.getUserId(),user.getUsername(),user.getEmail(),user.getRole().getRoleName()));
        return result;
    }

    /**
     * 登陆功能
     *
     * @param usernameoremail
     * @param password
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public String login(String usernameoremail, String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        String result = userService.login(usernameoremail, password);
        if ("success".equals(result)) {
            User user = userService.findUserByUsernameOrEmailAndPassword(usernameoremail, password);
            if (user != null) {
                //将User转换为SessionUser
                SessionUser sessionUser = userToSessionUser(user);
                SessionUtils.saveObjectToSession(request, response, sessionUser, "sessionUser");
            }
        }
        return result;
    }

    /**
     * 管理员添加用户功能
     *
     * @param user
     * @return
     */
    @PostMapping("/usermgn/user/save")
    @ResponseBody
    public UserInfo saveUser(User user, String roleName) {
        Role role = roleService.findByRoleName(roleName);
        user.setRole(role);
        user.setPassword(USER_INIT_PASSWORD);
        String result = userService.saveUser(user);
        UserInfo userInfo =new UserInfo(user.getUsername(),user.getEmail(),user.getRole().getRoleName(),result);
        userInfo.setUserId(user.getUserId());
        //同步搜索库
        esUserService.save(new EsUser(user.getUserId(),user.getUsername(),user.getEmail(),user.getRole().getRoleName()));

        return userInfo;
    }

    /**
     * 删除用户
     *
     * @return
     */
    @PostMapping("/usermgn/user/del")
    @ResponseBody
    public String delMeeting(Integer userId) {

        String result = userService.delUser(userId);

       //同步es
        esUserService.delEsUserById(userId);

        return result;
    }

    /**
     * 重置密码
     */
    @PostMapping("/usermgn/user/resetPsw")
    @ResponseBody
    public String resetPsw(String email){
        System.out.println("email:"+email);
        String result = userService.resetPwd(email);
        return result;
    }

    /**
     * 根据用户名进行查询
     */
    @GetMapping("/to/control/user/search")
    public String searchUser(String mrkey,Model model,
                        HttpServletRequest request){
        List<Role> roleList = roleService.findAllRoles();
        List<EsUser> esUserList = esUserService.findEsUserByUsername(mrkey);
        MRPage userPage = new MRPage(esUserList,
                1,
                1,
                esUserList.size(),
                1);
        model.addAttribute("userPage", userPage);
        model.addAttribute("roleList", roleList);
        UserUtils.setUserIndex(model, request);
        return "usermgn/user-manage";

    }

    /**
     * 将User转换为SessionUser
     *
     * @param user
     * @return
     */
    private static SessionUser userToSessionUser(User user) {
        SessionUser sessionUser = new SessionUser(user.getUserId(),
                user.getUsername(),
                user.getRole().getRoleName(),
                user.getHeadUrl());
        return sessionUser;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        System.out.println("....");
        //清除cookie和session
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
        }
        request.getSession().removeAttribute("sessionUser");
        return "redirect:/index";
    }

    /**
     *修改用户名
     */
    @PostMapping("/user/username/reset")
    @ResponseBody
    public String usernameReset(Integer userId,
                                String newUsername,
                                HttpServletRequest request,
                                HttpServletResponse response){

        System.out.println("userId = " + userId);
        System.out.println("newUsername = " + newUsername);

        String result = userService.userNameReset(userId,newUsername);
        if ( "success".equals(result)){
            //修改用户名后 同步es库
            EsUser esUser = esUserService.findEsUserById(userId);
            esUser.setUsername(newUsername);
            esUserService.save(esUser);

            System.out.println(" success " );
            SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
            sessionUser.setUsername(newUsername);
            SessionUtils.saveObjectToSession(request,response,sessionUser,"sessionUser");
            return "success";
        } else{
            return "userNameExist";
        }
    }

    /**
     *修改密码
     */
    @PostMapping("/user/userpwd/reset")
    @ResponseBody
    public String userpwdReset(Integer userId, String oldUserpwd, String newUserpwd){

        System.out.println("userId = " + userId);
        System.out.println("oldUserpwd = " + oldUserpwd);
        System.out.println("newUserpwd = " + newUserpwd);

        String result = userService.userPwdReset(userId,oldUserpwd,newUserpwd);
        if ("success".equals(result)){
            System.out.println("result = " + result);
            return "success";
        }
        else { return "oldUserpwdFalse"; }
    }
}

