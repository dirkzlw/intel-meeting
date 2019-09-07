package com.intel.meeting.controller;

import com.intel.meeting.po.User;
import com.intel.meeting.po.UserAuth;
import com.intel.meeting.service.UserAuthService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.FastDFSUtils;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.utils.UserUtils;
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
import java.util.List;

/**
 * @author Ranger
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


    @GetMapping("/to/usermgn/auth-check")
    public String toAuthCheck(Model model,
                              HttpServletRequest request,
                              @RequestParam(required = false) Integer page){
        if (page == null) {
            page = 0;
        }
        Page<UserAuth> userAuthPage= userAuthService.findUserAuthByPage(page, 9);
        MRPage mrPageInfo = new MRPage(userAuthPage.getContent(),
                page + 1,
                userAuthPage.getTotalPages(),
                (int) userAuthPage.getTotalElements(),
                2);
        model.addAttribute("authPage", mrPageInfo);
        UserUtils.setUserIndex(model, request);
        return "usermgn/auth-check";
    }

    /**
     * 用户认证
     *
     * @param userAuth
     * @param authImg
     * @return
     */
    @PostMapping("/he/user/auth")
    @ResponseBody
    public String userAuth(UserAuth userAuth,
                           MultipartFile authImg,
                           HttpServletRequest request) {
        try {
            String authUrl = FastDFSUtils.uploadFile(FDFSDFS_CLIENT_PAHT,
                    FDFSDFS_ADDRESS,
                    authImg);
            userAuth.setAuthUrl(authUrl);
            userAuth.setAuthStatus(2);
            //从session中获取当前用户，保存auth到当前用户
            SessionUser sessionUser = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");
            User user = userService.findUserById(sessionUser.getUserId());
            //先保存认证，用户才能关联这条认证
            userAuthService.saveUserAuth(userAuth);
            user.setUserAuth(userAuth);
            userService.saveUserAuth(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

}
