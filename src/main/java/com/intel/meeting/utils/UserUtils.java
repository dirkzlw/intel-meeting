package com.intel.meeting.utils;

import com.intel.meeting.vo.SessionUser;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ranger
 * @create 2019-06-04 21:43
 */
public class UserUtils {

    public static void setUserIndex(Model model, HttpServletRequest request) {
        //从session中获取author，判断是否登录
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new SessionUser(null,
                    null,
                    "用户",
                    null);
        }
        model.addAttribute("user", sessionUser);
    }

}
