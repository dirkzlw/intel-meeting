package com.intel.meeting.service.impl;

<<<<<<< HEAD
import com.intel.meeting.po.MeetingRoom;
=======
>>>>>>> origin/master
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
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String savaUser(User user) {
        List<User> userList = userRepository.findDistinctByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (user.getUserId() == null || "".equals(user.getUserId())) {
            for (User user1 : userList) {
                if (user1.getUsername().equals(user.getUsername()) || user1.getEmail().equals(user.getEmail()))
                    //1. 判断该用户是否存在
                    return "exist";
            }
            User save = userRepository.save(user);
            System.out.println("save = " + save);
            return "save";
        } else {
            //修改用户名
            for (User user1 : userList) {
                //如果有用户名重复情况
                if (user1.getUsername().equals(user.getUsername()) && user1.getEmail().equals(user.getEmail())) {
                    if (user1.getUserId() == user.getUserId()) {
                        userRepository.save(user);
                        return "save";
                    } else {
                        //出现重复情况
                        return "exist";
                    }
                }
            }
            userRepository.save(user);
            return "save";
        }
    }
}
