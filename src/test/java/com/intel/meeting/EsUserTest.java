package com.intel.meeting;

import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsUser;
import com.intel.meeting.repository.es.EsUserRepository;
import com.intel.meeting.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IntelMeetingApplication.class)
public class EsUserTest {
    @Autowired
    private EsUserRepository esUserRepository;
    @Autowired
    private UserService userService;


    @Test
    public void test(){
        esUserRepository.deleteAll();
        List<User> userList = userService.findAllUser();
        EsUser esUser = new EsUser(null,null,null,null);
        for (User user:userList){
            esUser.setUserId(user.getUserId());
            esUser.setUsername(user.getUsername());
            esUser.setEmail(user.getEmail());
            esUser.setRole(user.getRole().getRoleName());
            esUserRepository.save(esUser);
        }
    }
    @Test
    public  void testA(){
        List<EsUser> esUsersList = esUserRepository.findByUsernameContaining("slp");
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        for(EsUser esUser: esUsersList){
            System.out.println(esUser);
        }
    }

}
