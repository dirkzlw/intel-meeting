package com.intel.meeting.controller;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsUser;
import com.intel.meeting.service.RoleService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.service.es.EsUserService;
import com.intel.meeting.utils.FastDFSUtils;
import com.intel.meeting.utils.MD5Utils;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理关于用户的请求
 *
 * @author Intel-Meeting
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
    @Value("${FDFSDFS_CLIENT_PAHT}")
    private String FDFSDFS_CLIENT_PAHT;
    @Value("${FDFSDFS_ADDRESS}")
    private String FDFSDFS_ADDRESS;

    /**
     * 跳转至用户管理--账户管理界面
     *
     * @param model   模型
     * @param page    当前页
     * @param request 请求
     * @return 用户管理--账户管理
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
            UserInfo userInfo = new UserInfo(user.getUsername(), user.getEmail(), user.getRole().getRoleName());
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
        SessionUtils.setUserIndex(model, request);
        return "usermgn/user-manage";
    }

    /**
     * 跳转至用户个人信息界面
     *
     * @param model   模型
     * @param request 请求
     * @return 用户信息
     */
    @GetMapping("/to/user/msg")
    public String toUserMsg(Model model,
                            HttpServletRequest request) {

        SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
        User user = userService.findUserById(sessionUser.getUserId());
        model.addAttribute("umsg", user);

        SessionUtils.setUserIndex(model, request);

        return "user/user-msg";
    }

    /**
     * 跳转到登陆界面
     *
     * @return 登录界面
     */
    @GetMapping("/to/user/login")
    public String toUserLogin() {
        return "user/login";
    }

    /**
     * 验证用户名和邮箱的重复性
     *
     * @param username 用户名
     * @param email    邮箱
     * @return 返回执行结果
     */
    @PostMapping("/user/getcode")
    @ResponseBody
    public String getVCode(String username, String email) {
        String result = userService.getVCode(username, email);
        return result;
    }

    /**
     * 忘记密码
     *
     * @param email 邮箱
     * @return 忘记密码执行结果
     */
    @PostMapping("/user/forgetpwd")
    @ResponseBody
    public String forgetPwd(String email) {
        String result = userService.forgetPwd(email);
        if ("success".equals(result)) {
            return "success";
        } else {
            return "notFound";
        }
    }

    /**
     * 注册功能
     *
     * @param user     用户对象
     * @param code     验证码
     * @param request  请求
     * @param response 响应
     * @return 注册结果
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
        esUserService.save(new EsUser(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole().getRoleName()));
        return result;
    }

    /**
     * 登陆功能
     *
     * @param usernameoremail 用户名或邮箱
     * @param password        密码
     * @param request         请求
     * @param response        响应
     * @return 登录结果
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
     * @param user 用户对象
     * @param roleName 角色名称
     * @return 保存结果
     */
    @PostMapping("/usermgn/user/save")
    @ResponseBody
    public UserInfo saveUser(User user, String roleName) {
        String result;
        if (user.getUserId() == null) {
            // 添加
            Role role = roleService.findByRoleName(roleName);
            user.setRole(role);
            user.setPassword(MD5Utils.md5(USER_INIT_PASSWORD));
            user.setHeadUrl(USER_INIT_HEAD_URL);
            result = userService.saveUser(user);
        } else {
            //修改
            Role role = roleService.findByRoleName(roleName);
            user.setRole(role);
            result = userService.saveUser(user);
        }
        UserInfo userInfo = new UserInfo(user.getUsername(), user.getEmail(), user.getRole().getRoleName(), result);
        userInfo.setUserId(user.getUserId());
        //同步搜索库
        if (result == "save") {
            esUserService.save(new EsUser(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole().getRoleName()));
        }
        return userInfo;
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 删除结果
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
     *
     * @param userId 用户Id
     * @return 执行结果
     */
    @PostMapping("/usermgn/user/resetPwd")
    @ResponseBody
    public String resetPwd(Integer userId) {
        User user = userService.findUserById(userId);
        user.setPassword(MD5Utils.md5(USER_INIT_PASSWORD));
        String result = userService.userPwdReset(user);

        return result;
    }

    /**
     * 查询用户
     *
     * @param mrkey   关键字
     * @param model   模型
     * @param request 请求
     * @return 查询结果
     */
    @GetMapping("/to/control/user/search")
    public String searchUser(String mrkey, Model model,
                             HttpServletRequest request) {
        List<Role> roleList = roleService.findAllRoles();
        List<EsUser> esUserList = esUserService.findEsUserByUsername(mrkey);
        MRPage userPage = new MRPage(esUserList,
                1,
                1,
                esUserList.size(),
                1);
        model.addAttribute("userPage", userPage);
        model.addAttribute("roleList", roleList);
        SessionUtils.setUserIndex(model, request);
        return "usermgn/user-manage";

    }

    /**
     * 将User转换为SessionUser
     *
     * @param user 用户对象
     * @return SessionUser对象
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
     *
     * @param request 请求
     * @return 退出登录结果
     */
    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        //清除cookie和session
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
        }
        request.getSession().removeAttribute("sessionUser");
        return "redirect:/index";
    }

    /**
     * 修改用户名
     *
     * @param userId      用户id
     * @param newUsername 新用户名
     * @param request     请求
     * @param response    响应
     * @return 修改结果
     */
    @PostMapping("/user/username/reset")
    @ResponseBody
    public String usernameReset(Integer userId,
                                String newUsername,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        String result = userService.userNameReset(userId, newUsername);
        if ("success".equals(result)) {
            //修改用户名后 同步es库
            EsUser esUser = esUserService.findEsUserById(userId);
            esUser.setUsername(newUsername);
            esUserService.save(esUser);

            SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
            sessionUser.setUsername(newUsername);
            SessionUtils.saveObjectToSession(request, response, sessionUser, "sessionUser");
            return "success";
        } else {
            return "userNameExist";
        }
    }

    /**
     * 修改密码
     *
     * @param userId     用户名
     * @param oldUserpwd 旧密码
     * @param newUserpwd 新密码
     * @return 修改结果
     */
    @PostMapping("/user/userpwd/reset")
    @ResponseBody
    public String userpwdReset(Integer userId, String oldUserpwd, String newUserpwd) {
        String result = userService.userPwdReset(userId, oldUserpwd, newUserpwd);
        if ("success".equals(result)) {
            return "success";
        } else {
            return "oldUserpwdFalse";
        }
    }

    /**
     * 修改邮箱
     *
     * @param userId   用户ID
     * @param newEmail 用户修改后邮箱
     * @return 修改结果
     */
    @PostMapping("/user/email/reset")
    @ResponseBody
    public String userEamilReset(Integer userId,
                                 String newEmail) {

        String result = userService.userEmailReset(userId, newEmail);

        if ("success".equals(result)) {

            EsUser esUser = esUserService.findEsUserById(userId);
            esUser.setEmail(newEmail);
            esUserService.save(esUser);
        }
        return result;
    }

    /**
     * 修改头像
     *
     * @param userId     用户ID
     * @param newHeadUrl 用户新头像
     * @param request    请求
     * @param response   响应
     * @return 修改结果
     */
    @PostMapping("/user/headurl/reset")
    @ResponseBody
    public String HeadUrlReset(Integer userId,
                               MultipartFile newHeadUrl,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        //上传文件至服务器
        String newUrl = FastDFSUtils.uploadFile(FDFSDFS_CLIENT_PAHT,
                FDFSDFS_ADDRESS,
                newHeadUrl);
        String result = "";
        if (newUrl != null || !"".equals(newUrl)) {
            result = userService.HeadUrlReset(userId, newUrl);
        }
        if ("success".equals(result)) {
            SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
            sessionUser.setHeadUrl(newUrl);
            SessionUtils.saveObjectToSession(request, response, sessionUser, "sessionUser");
            return "success";
        }
        return "fail";
    }

    /**
     * 跳转至联系我们界面
     *
     * @param model   模型
     * @param request 请求
     * @return 联系我们
     */
    @GetMapping("/to/user/contact")
    public String toContact(Model model,
                            HttpServletRequest request) {
        SessionUtils.setUserIndex(model, request);
        return "index/user-contact";
    }

    /**
     * 联系我们
     *
     * @param realname   姓名
     * @param email      联系方式
     * @param suggestion 建议
     * @return 执行结果
     */
    @PostMapping("/user/contact")
    @ResponseBody
    public String contactWe(String realname, String email, String suggestion) {
        String result = userService.contactWe(realname, email, suggestion);
        return result;
    }

}

