package com.intel.meeting.service.impl;

import com.intel.meeting.po.User;
import com.intel.meeting.po.UserAuth;
import com.intel.meeting.repository.UserAuthRepository;
import com.intel.meeting.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-10:55
 */
@Service
public class UserAuthServiceImpl implements UserAuthService{

    @Autowired
    private UserAuthRepository userAuthRepository;

    /**
     * 保存认证
     * @param userAuth
     */
    @Override
    public void saveUserAuth(UserAuth userAuth) {
        userAuthRepository.save(userAuth);
    }

    /**
     * 查询某状态的认证人数
     * @param i
     * @return
     */
    @Override
    public Integer findUsersNotAuth(Integer i) {
        return userAuthRepository.countByAuthStatus(i);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<UserAuth> findUserAuthByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<UserAuth> userPage = userAuthRepository.findAll(pageable);
        return userPage;
    }
}
