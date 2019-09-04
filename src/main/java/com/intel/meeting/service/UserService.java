package com.intel.meeting.service;

import com.intel.meeting.po.User;

/**
 * @author ranger
 * @create 2019-09 -03-13:53
 */


public interface UserService {

    String saveUser(User user);
    String getVCode(String username,String email);
    String register(User user,String vcode);

    String login(String usernameoremail, String password);
}