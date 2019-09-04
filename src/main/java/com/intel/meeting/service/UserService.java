package com.intel.meeting.service;

import com.intel.meeting.po.User;

/**
 * @author ranger
 * @create 2019-09 -03-13:53
 */


public interface UserService {

    String getVCode(String email,String userid);

    String savaUser(User user);

}
