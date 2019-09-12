package com.intel.meeting.utils;

import com.intel.meeting.vo.SessionUser;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Intel-Meeting
 * @create 2019-09-05 15:05
 */
public class SessionUtils {
    /**
     * 将对象保存在Session
     *
     * @param request     请求
     * @param response    响应
     * @param obj         要保存的对象
     * @param sessionName session的key
     */
    public static void saveObjectToSession(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Object obj,
                                           String sessionName) {
        //将JSESSIONID存于本地cookie中
        Cookie cookiesessionid = new Cookie("JSESSIONID", request.getSession().getId());
        cookiesessionid.setMaxAge(24 * 60 * 60);
        response.addCookie(cookiesessionid);

        //存Session
        //根据用户角色，设置用户权限
        HttpSession session = request.getSession();
        session.setAttribute(sessionName, obj);
        session.setMaxInactiveInterval(3 * 24 * 60);    //设置session生存时间
    }

    /**
     * 从session中获取对象
     *
     * @param request     请求
     * @param sessionName key
     * @return 返回session中存储的对象
     */
    public static Object getObjectFromSession(HttpServletRequest request, String sessionName) {
        return request.getSession().getAttribute(sessionName);
    }

    /**
     * 设置主页对象
     *
     * @param model   模型
     * @param request 请求
     */
    public static void setUserIndex(Model model, HttpServletRequest request) {
        //从session中获取user，判断是否登录
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
