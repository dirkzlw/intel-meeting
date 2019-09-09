package com.intel.meeting.service.impl;

import com.intel.meeting.po.User;
import com.intel.meeting.repository.UserRepository;
import com.intel.meeting.service.UserService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.utils.MD5Utils;
import com.intel.meeting.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ranger
 * @create 2019-09 -03-13:54
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    //注入javaMail发送器
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisOperations<String, String> redisTemplate;

    //邮件发送者
    @Value("${spring.mail.username}")
    private String fromEmail;
    //邮件默认标题
    @Value("${INTEL_MAIL_SUBJECT}")
    private String INTEL_MAIL_SUBJECT;
    //用户初始密码
    @Value("${USER_INIT_PASSWORD}")
    private String USER_INIT_PASSWORD;
    //用户初始头像
    @Value("${USER_INIT_HEAD_URL}")
    private String USER_INIT_HEAD_URL;

    @Override
    public String getVCode(String username, String email) {
        User user1 = userRepository.findByUsername(username);
        if (user1 != null) {
            return "userNameExist";
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return "emailExist";
        }

        /**
         * 用户名和邮箱不冲突，将用户名、邮箱以及随机生成的6位验证码
         * 以key:用户名-邮箱      value:验证码  的格式存在redis数据库
         * 过期时间设置为10min
         * 注册需要根据key取value，然后校对，可参考test下com.intel.meeting.TestRedis
         */
        String vcode = (int) ((Math.random() * 9 + 1) * 100000) + "";
        String key = username + "-" + email;
        redisTemplate.opsForValue().set(key, vcode, 10L, TimeUnit.MINUTES);


        /**
         * 下面是发送邮件
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,
                        email,
                        INTEL_MAIL_SUBJECT,
                        "您的验证码是" + vcode + ",有效期为10分钟。\n");
                //发送
                mailSender.send(message);
            }
        }).start();

        return "success";
    }

    /**
     * 注册
     *
     * @param user
     * @param vcode
     * @return
     */
    @Override
    public String register(User user, String vcode) {

        String key = user.getUsername() + "-" + user.getEmail();
        String code = redisTemplate.opsForValue().get(key);

        if (code == null) {
            return "NotMatch";
        } else if (code.equals(vcode)) {
            user.setHeadUrl(USER_INIT_HEAD_URL);
            user.setPassword(MD5Utils.md5(user.getPassword()));
            userRepository.save(user);
            redisTemplate.delete(key);
            return "success";
        }

        return "CodeError";
    }

    /**
     * 登录
     *
     * @param usernameoremail
     * @param password
     * @return
     */
    @Override
    public String login(String usernameoremail, String password) {
        System.out.println("usernameoremail = " + usernameoremail);
        System.out.println("password = " + password);
        List<User> userList = userRepository.findDistinctByUsernameOrEmail(usernameoremail, usernameoremail);
        if (userList == null) {
            return "NotExist";
        } else {
            for (User user : userList) {
                if (user.getPassword().equals(MD5Utils.md5(password))) {
                    return "success";
                }
            }
        }
        return "PwdError";
    }

    /**
     * 发送邮件，重置密码
     * @param email
     * @return
     */
    @Override
    public String resetPwd(String email) {

        User user = userRepository.findByEmail(email);
        String newPsw = (int) ((Math.random() * 9 + 1) * 100000) + "";
        user.setPassword(MD5Utils.md5(newPsw));
        userRepository.save(user);

        System.out.println(email);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,email,INTEL_MAIL_SUBJECT,"您重置后密码为:" + newPsw + ".\n");
                //发送
                mailSender.send(message);

            }
        }).start();

        return "success";
    }

    /**
     * 统计已注册人数
     * @return
     */
    @Override
    public int countRegistNum() {
        return (int) userRepository.count();
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public String saveUser(User user) {
        List<User> userList = userRepository.findDistinctByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (user.getUserId() == null || "".equals(user.getUserId())) {
            for (User user1 : userList) {
                if (user1.getUsername().equals(user.getUsername()) )
                    //1. 判断该用户是否存在
                    return "userNameExist";
                else if(user1.getEmail().equals(user.getEmail())){
                    return "emailExist";
                }
            }
            user.setPassword(MD5Utils.md5(USER_INIT_PASSWORD));
            user.setHeadUrl(USER_INIT_HEAD_URL);
            user.setPassword(MD5Utils.md5(USER_INIT_PASSWORD));
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

    /**
     * 分页查询用户
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<User> findUserByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "username"));
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage;
    }

    /**
     * 根据id删除用户
     *
     * @param userId
     * @return
     */
    @Override
    public String delUser(Integer userId) {
        userRepository.delete(userId);
        return "del";
    }

    /**
     * 根据用户名或者邮箱和密码获取用户
     *
     * @param usernameoremail
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernameOrEmailAndPassword(String usernameoremail, String password) {
        List<User> userList = userRepository.findDistinctByUsernameOrEmail(usernameoremail, usernameoremail);
        if (userList == null) {
            return null;
        } else {
            for (User user : userList) {
                if (user.getPassword().equals(MD5Utils.md5(password))) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public User findUserById(Integer userId) {
        return userRepository.findOne(userId);
    }

    /**
     * 处理没有签到
     *
     * @param reserveUser
     */
    @Override
    public void doNoSign(User reserveUser) {
        Integer warnNum = reserveUser.getWarnNum();
        if (warnNum == null) {
            warnNum = 0;
        }
        reserveUser.setWarnNum(warnNum + 1);
        //达到未签到上限
        if (reserveUser.getWarnNum() >= 5) {
            reserveUser.setUntilTime(DateUtils.getAfterThreeDate());
            reserveUser.setWarnNum(0);
        }
        userRepository.save(reserveUser);
    }

    /**
     * 保存用户认证
     * @param user
     */
    @Override
    public void saveUserAuth(User user) {
        userRepository.save(user);
    }

    public String userNameReset(Integer userId, String newUsername){

        User oldUser = userRepository.findOne(userId);
        User user1 = userRepository.findByUsername(newUsername);

        if (user1 == null){
            oldUser.setUsername(newUsername);
            userRepository.save(oldUser);
        }
        else {
            return "userNameExist";
        }
        return "success";
    }
}
