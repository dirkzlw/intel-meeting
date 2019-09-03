package com.intel.meeting.service.impl;

import com.intel.meeting.po.User;
import com.intel.meeting.repository.UserRepository;
import com.intel.meeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-13:54
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public String getVCode(String email,String username){
        User user = userRepository.findByEmail(email);
        if(user!=null){
            return "emailExist";
        }
        User user1 = userRepository.findByUsername(username);
        if (user1!=null){
            return "userNameExist";
        }

        return "success";
    }

    public void save(User user){
        userRepository.save(user);
    }
}
