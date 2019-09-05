package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.utils.UserUtils;
import com.intel.meeting.vo.MRPage;
import com.intel.meeting.vo.RtnIdInfo;
import com.intel.meeting.vo.SessionUser;
import com.intel.meeting.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    //用户初始角色
    @Value("${USER_INIT_ROLE}")
    private String USER_INIT_ROLE;

    /**
     * 跳转到查询用户界面
     * @return
     */
    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(Model model,
                               @RequestParam(required = false) Integer page,
                               HttpServletRequest request){
        List<Role> roleList = roleService.findAllRoles();
        System.out.println("roleList = " + roleList);
        if (page == null) {
            page = 0;
        }
        Page<User> userPage =userService.findUserByPage(page, 10);
        List<User> userList=userPage.getContent();
        List<UserInfo> userInfoList=new ArrayList<>();
        for(User user:userList){
            UserInfo userInfo=new UserInfo(user.getUsername(),"******",user.getEmail(),user.getRole().getRoleName());
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
    public RtnIdInfo register(User user,String code,
                              HttpServletRequest request,
                              HttpServletResponse response){
        Role role = null;
        role = roleService.findByRoleName(new String(USER_INIT_ROLE));
        user.setRole(role);
        String result = userService.register(user,code);
        if ("success".equals(result)){
            //将User转换为SessionUser
            SessionUser sessionUser = userToSessionUser(user);
            SessionUtils.saveObjectToSession(request, response, sessionUser, "sessionUser");
        }
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
    public String login(String usernameoremail, String password,
                           HttpServletRequest request,
                           HttpServletResponse response){
        String result = userService.login(usernameoremail, password);
        if("success".equals(result)){
            User user = userService.findUserByUsernameOrEmailAndPassword(usernameoremail, password);
            if (user !=null){
                //将User转换为SessionUser
                SessionUser sessionUser = userToSessionUser(user);
                SessionUtils.saveObjectToSession(request, response, sessionUser, "sessionUser");
            }
        }
        return result;
    }

    /**
     * 管理员添加用户功能
     * @param user
     * @return
     */
    @PostMapping("/usermgn/user/save")
    @ResponseBody
    public RtnIdInfo saveUser(User user, String roleName){
//        System.out.println(roleName);
        Role role=roleService.findByRoleName(roleName);
        user.setRole(role);
        System.out.println("user:"+user);
       String result = userService.saveUser(user);

        return new RtnIdInfo(result,user.getUserId());
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

//        //同步es
//        emrService.delEsMeetingRoomById(meetingId);

        return result;
    }

    /**
     * 将User转换为SessionUser
     * @param user
     * @return
     */
    private static SessionUser userToSessionUser(User user){
        SessionUser sessionUser = new SessionUser(user.getUserId(),
                user.getUsername(),
                user.getRole().getRoleName(),
                user.getHeadUrl());
        return sessionUser;
    }


}
