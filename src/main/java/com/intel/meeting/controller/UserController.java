package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.vo.MRPage;
import com.intel.meeting.vo.RtnIdInfo;
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
    @Value("${USER_INIT_ROLE}")
    private String USER_INIT_ROLE;
    @Value("${USER_INIT_PASSWORD}")
    private String USER_INIT_PASSWORD;

    /**
     * 跳转到查询用户界面
     * @return
     */
    @GetMapping("/to/usermgn/user-manage")
    public String toUserManage(Model model,
                               @RequestParam(required = false) Integer page){
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
    public RtnIdInfo login(String usernameoremail, String password,
                           HttpServletRequest request,
                           HttpServletResponse response){
        String result = userService.login(usernameoremail, password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if("success".equals(result)){
                    User user = userService.findUserByUsernameOrEmailAndPassword(usernameoremail, password);
                    if (user !=null){
                        //将JSESSIONID存于本地cookie中
                        Cookie cookiesessionid = new Cookie("JSESSIONID", request.getSession().getId());
                        cookiesessionid.setMaxAge(24 * 60 * 60);
                        response.addCookie(cookiesessionid);

                        //存Session
                        //根据用户角色，设置用户权限
                        HttpSession session = request.getSession();
                        session.setAttribute("sessionUser", user);
                        session.setMaxInactiveInterval(3 * 24 * 60);    //设置session生存时间
                    }
                }
            }
        }).start();
        return new RtnIdInfo(result,0);
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
}
