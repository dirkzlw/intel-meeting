package com.intel.meeting.service;

import com.intel.meeting.po.UserAuth;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09 -03-10:54
 */
public interface UserAuthService {
    void saveUserAuth(UserAuth userAuth);

    int countUserAuthStatus(int i);

    Page<UserAuth> findUserAuthByPage(Integer page, Integer size);

    UserAuth findUserAuthById(Integer authId);

    String authPass(Integer authId);

    String authNoPass(Integer authId, String noPassReason);

}
