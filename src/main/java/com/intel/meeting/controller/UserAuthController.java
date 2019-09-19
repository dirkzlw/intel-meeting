package com.intel.meeting.controller;

import com.intel.meeting.po.User;
import com.intel.meeting.po.UserAuth;
import com.intel.meeting.service.UserAuthService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.FastDFSUtils;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.vo.MRPage;
import com.intel.meeting.vo.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Intel-Meeting
 * @create 2019-09-07 16:44
 */
@Controller
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @Value("${FDFSDFS_CLIENT_PAHT}")
    private String FDFSDFS_CLIENT_PAHT;
    @Value("${FDFSDFS_ADDRESS}")
    private String FDFSDFS_ADDRESS;

    /**
     * 跳转至用户认证
     *
     * @param model   模型
     * @param request 请求
     * @return 用户认证界面
     */
    @GetMapping("/to/user/auth")
    public String toUserAuth(Model model,
                             HttpServletRequest request) {
        SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
        User user = userService.findUserById(sessionUser.getUserId());
        SessionUtils.setUserIndex(model, request);
        if (user.getUserAuth() != null) {
            //获取认证状态 通过--审核--未通过
            model.addAttribute("authStatus", user.getUserAuth().getAuthStatus());
        } else {
            //尚未提交认证
            model.addAttribute("authStatus", 0);
        }
        return "user/user-auth";
    }

    /**
     * 跳转至用户认证编辑界面
     *
     * @param model   模型
     * @param request 请求
     * @param userId  用户id
     * @return 认证再次编辑界面
     */
    @GetMapping("/to/user/auth/edit")
    public String userAuthEdit(Model model,
                               HttpServletRequest request,
                               Integer userId) {
        UserAuth userAuth = userService.findUserById(userId).getUserAuth();

        model.addAttribute("userAuth", userAuth);
        SessionUtils.setUserIndex(model, request);
        return "user/auth-edit";
    }

    /**
     * 用户认证
     *
     * @param userAuth 用户认证
     * @param authImg  认证资质
     * @param request 请求
     * @return 认证结果
     */
    @PostMapping("/he/user/auth")
    @ResponseBody
    public String userAuth(UserAuth userAuth,
                           MultipartFile authImg,
                           HttpServletRequest request) {
        try {
            //从session中获取当前用户，保存auth到当前用户
            SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
            User user = userService.findUserById(sessionUser.getUserId());
            UserAuth exitAuth = user.getUserAuth();
            String authUrl = FastDFSUtils.uploadFile(FDFSDFS_CLIENT_PAHT,
                    FDFSDFS_ADDRESS,
                    authImg);
            if (exitAuth == null) {
                userAuth.setAuthUrl(authUrl);
                userAuth.setAuthStatus(2);
                user.setUserAuth(userAuth);
                //先保存认证，用户才能关联这条认证
                userAuthService.saveUserAuth(userAuth);
            } else {
                exitAuth.setAuthUrl(authUrl);
                exitAuth.setJobNum(userAuth.getJobNum());
                exitAuth.setRealname(userAuth.getRealname());
                exitAuth.setAuthStatus(2);
                user.setUserAuth(exitAuth);
            }
            userService.saveUserAuth(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }


    /**
     * 跳转至认证审核界面
     *
     * @param model   模型
     * @param request 请求
     * @param page    分页对象
     * @return 认证审核界面
     */
    @GetMapping("/to/usermgn/auth-check")
    public String toAuthCheck(Model model,
                              HttpServletRequest request,
                              @RequestParam(required = false) Integer page) {
        if (page == null) {
            page = 0;
        }
        Page<UserAuth> userAuthPage = userAuthService.findUserAuthByPage(page, 9);
        MRPage mrPageInfo = new MRPage(userAuthPage.getContent(),
                page + 1,
                userAuthPage.getTotalPages(),
                (int) userAuthPage.getTotalElements(),
                2);
        model.addAttribute("authPage", mrPageInfo);
        SessionUtils.setUserIndex(model, request);
        return "usermgn/auth-check";
    }

    /**
     * 通过认证审核
     *
     * @param authId 认证id
     * @return 通过结果
     */
    @PostMapping("/user/auth/pass")
    @ResponseBody
    public String authPass(Integer authId) {
        String rtn = userAuthService.authPass(authId);
        return rtn;
    }

    /**
     * 认证不通过审核
     *
     * @param authId       认证id
     * @param noPassReason 不通过的理由
     * @return 不通过的结果
     */
    @PostMapping("/user/auth/nopass")
    @ResponseBody
    public String authNoPass(Integer authId,
                             String noPassReason) {
        String rtn = userAuthService.authNoPass(authId, noPassReason);
        return rtn;
    }
}
