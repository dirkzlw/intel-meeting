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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private RedisTemplate<String, String> redisTemplate;

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

    @Value("${ADMIN_EMAIL}")
    private String ADMIN_EMAIL;

    /**
     * 注册时获取验证码
     *
     * @param username
     * @param email
     * @return
     */
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
        List<User> userList = userRepository.findDistinctByUsernameOrEmail(usernameoremail, usernameoremail);
        if (userList == null || userList.size() == 0) {
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
     * 忘记密码，发邮件重置
     *
     * @param email
     * @return
     */
    @Override
    public String forgetPwd(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String newPsw = (int) ((Math.random() * 9 + 1) * 100000) + "";
            user.setPassword(MD5Utils.md5(newPsw));
            userRepository.save(user);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleMailMessage message = MailUtils.getMailMessage(fromEmail, email, INTEL_MAIL_SUBJECT, "您重置后密码为:" + newPsw + ".\n");
                    //发送
                    mailSender.send(message);
                }
            }).start();
            return "success";
        } else {
            return "notFound";
        }
    }

    /**
     * 统计已注册人数
     *
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
                if (user1.getUsername().equals(user.getUsername()))
                    //1. 判断该用户是否存在
                    return "userNameExist";
                else if (user1.getEmail().equals(user.getEmail())) {
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
     * 查询所有用户
     */
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
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

            //发送邮件提醒
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,
                            reserveUser.getEmail(),
                            INTEL_MAIL_SUBJECT,
                            "您使用会议室未签到次数达到5次上线，" +
                                    "现对您做出封号三天处理，三天内禁止预定，" +
                                    "解封时间为：" + reserveUser.getUntilTime() + "\n");
                    //发送
                    mailSender.send(message);
                }
            }).start();
        } else {
            //发送邮件提醒
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,
                            reserveUser.getEmail(),
                            INTEL_MAIL_SUBJECT,
                            "您最近一次使用会议室未签到，未签到次数已达到" +
                                    reserveUser.getWarnNum() + "次，" +
                                    "累计次数达到5次，系统会做出禁止预定处理！！！\n");
                    //发送
                    mailSender.send(message);
                }
            }).start();
        }
        userRepository.save(reserveUser);
    }

    /**
     * 保存用户认证
     *
     * @param user
     */
    @Override
    public void saveUserAuth(User user) {
        userRepository.save(user);
    }

    /**
     * 用户个人资料处 修改用户名
     *
     * @param userId
     * @param newUsername
     * @return
     */
    @Override
    public String userNameReset(Integer userId, String newUsername) {
        User oldUser = userRepository.findOne(userId);
        User user1 = userRepository.findByUsername(newUsername);
        if (user1 == null) {
            oldUser.setUsername(newUsername);
            userRepository.save(oldUser);
        } else {
            return "userNameExist";
        }
        return "success";
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @Override
    public String userPwdReset(User user) {
        userRepository.save(user);
        return "success";
    }

    /**
     * 用户个人资料处 修改密码
     *
     * @param userId
     * @param oldUserpwd
     * @param newUserpwd
     * @return
     */
    @Override
    public String userPwdReset(Integer userId, String oldUserpwd, String newUserpwd) {
        User oldUser = userRepository.findOne(userId);
        if (oldUser.getPassword().equals(MD5Utils.md5(oldUserpwd))) {
            oldUser.setPassword(MD5Utils.md5(newUserpwd));
            userRepository.save(oldUser);
            return "success";
        } else {
            return "oldUserpwdFalse";
        }
    }

    /**
     * 用户个人资料处 修改邮箱
     *
     * @param userId
     * @param newEmail
     * @return
     */
    @Override
    public String userEmailReset(Integer userId, String newEmail) {
        User oldeUser = userRepository.findOne(userId);
        User user2 = userRepository.findByEmail(newEmail);
        if (user2 == null) {
            oldeUser.setEmail(newEmail);
            userRepository.save(oldeUser);
        } else {
            return "EmailExist";
        }
        return "success";
    }

    /**
     * 用户个人资料处 修改头像
     *
     * @param userId
     * @param newHeadUrl
     * @return
     */
    @Override
    public String HeadUrlReset(Integer userId, String newHeadUrl) {
        User user = userRepository.findOne(userId);
        user.setHeadUrl(newHeadUrl);
        userRepository.save(user);
        return "success";
    }

    /**
     * 判断用户是否在黑名单
     *
     * @param userId
     * @return User
     */
    @Override
    public boolean isBlackList(Integer userId) {
        User user = userRepository.findOne(userId);
        String untilTime = user.getUntilTime();
        if (untilTime == null || "".equals(untilTime)) {
            return false;
        } else {
            long unTime = DateUtils.stringToTime(untilTime);
            long nowTime = new Date().getTime();
            if (unTime - nowTime > 0) {
                return true;
            } else {
                //封号时间过期
                user.setUntilTime("");
                userRepository.save(user);
                return false;
            }
        }
    }

    /**
     * 联系我们
     *
     * @return
     */
    @Override
    public String contactWe(String realname, String email, String suggestion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,
                        ADMIN_EMAIL,
                        INTEL_MAIL_SUBJECT,
                        "来自" + realname +"的建议.\n"+ "该用户联系方式为" + email +".\n" + "建议为：" + suggestion);
                    //发送
                mailSender.send(message);
            }
        }).start();
        return "success";
    }

}
