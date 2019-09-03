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

    @Override
    public String getVCode(String username,String email){
        User user = userRepository.findByEmail(email);
        System.out.println("user = " + user);
        if(user!=null){
            return "emailExist";
        }
        User user1 = userRepository.findByUsername(username);
        System.out.println("user1 = " + user1);
        if (user1!=null){
            return "userNameExist";
        }

        return "success";
    }

    @Override
    public void save(User user){
        userRepository.save(user);
    }
}
