package com.intel.meeting.utils;

import com.intel.meeting.po.User;
import com.intel.meeting.vo.SessionUser;
import com.intel.meeting.vo.UserIndex;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ranger
 * @create 2019-06-04 21:43
 */
public class UserUtils {

    public static void setUserIndex(Model model, HttpServletRequest request){
        //从session中获取author，判断是否登录
        SessionUser user = (SessionUser) request.getSession().getAttribute("sessionUser");
        UserIndex userIndex = null;
        if(user != null){
            userIndex = new UserIndex(user.getUserId(), user.getHeadUrl());
        }else{
            userIndex = new UserIndex(null, null);
        }
        model.addAttribute("user", userIndex);
    }

}
