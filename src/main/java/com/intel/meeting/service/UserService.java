package com.intel.meeting.service;

import com.intel.meeting.po.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09 -03-13:53
 */


public interface UserService {

    int countRegistNum();

    String saveUser(User user);

    String getVCode(String username, String email);

    String register(User user, String vcode);

    //忘记密码
    String forgetPwd(String email);

    String login(String usernameoremail, String password);

    Page<User> findUserByPage(Integer page, Integer size);

    String delUser(Integer userId);

    User findUserByUsernameOrEmailAndPassword(String usernameoremail, String password);

    User findUserById(Integer userId);

    void doNoSign(User reserveUser);

    void saveUserAuth(User user);

    List<User> findAllUser();

    String userNameReset(Integer userId, String newUsername);

    String userPwdReset(User user);

    String userEmailReset(Integer userId, String newEmail);

    String HeadUrlReset(Integer userId, String newHeadUrl);

    String userPwdReset(Integer userId, String oldUserpwd, String newUserpwd);

    boolean isBlackList(Integer userId);

    String contactWe(String realname, String email, String suggestion);
}
