package com.intel.meeting.service;

import com.intel.meeting.po.UserAuth;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-10:54
 */
public interface UserAuthService {
    void saveUserAuth(UserAuth userAuth);

    Integer findUsersNotAuth(Integer i);

    Page<UserAuth> findUserAuthByPage(Integer page, Integer size);
}
