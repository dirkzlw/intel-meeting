package com.intel.meeting.service.impl;

import com.intel.meeting.po.User;
import com.intel.meeting.po.UserAuth;
import com.intel.meeting.repository.UserAuthRepository;
import com.intel.meeting.service.UserAuthService;
import com.intel.meeting.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    //注入javaMail发送器
    @Autowired
    private JavaMailSender mailSender;

    //邮件发送者
    @Value("${spring.mail.username}")
    private String fromEmail;
    //邮件默认标题
    @Value("${INTEL_MAIL_SUBJECT}")
    private String INTEL_MAIL_SUBJECT;

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
    public int countUserAuthStatus(int i) {
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
        //设置查询条件
        UserAuth userAuth = new UserAuth(null, null, null, 2, null);
        //设置条件
        Example<UserAuth> example = Example.of(userAuth);
        Pageable pageable = new PageRequest(page, size);
        Page<UserAuth> userPage = userAuthRepository.findAll(example, pageable);
        return userPage;
    }

    /**
     * 根据id查询用户认证
     * @param authId
     * @return
     */
    @Override
    public UserAuth findUserAuthById(Integer authId) {
        return null;
    }

    /**
     * 通过认证
     * @param authId
     * @return
     */
    @Override
    public String authPass(Integer authId) {
        UserAuth one = userAuthRepository.findOne(authId);
        try {
            one.setAuthStatus(1);
            userAuthRepository.save(one);
        }catch (Exception e){
            return "fail";
        }

        //审核通过，发送邮件通知
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,
                        one.getUser().getEmail(),
                        INTEL_MAIL_SUBJECT,
                        "您已通过身份认证，请随心使用系统。.\n");
                //发送
                mailSender.send(message);
            }
        }).start();

        return "success";
    }

    /**
     * 不通过认证
     * @param authId
     * @param noPassReason
     * @return
     */
    @Override
    public String authNoPass(Integer authId, String noPassReason) {
        UserAuth one = userAuthRepository.findOne(authId);
        try {
            one.setAuthStatus(3);
            userAuthRepository.save(one);
        }catch (Exception e){
            return "fail";
        }

        //审核通过，发送邮件通知
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = MailUtils.getMailMessage(fromEmail,
                        one.getUser().getEmail(),
                        INTEL_MAIL_SUBJECT,
                        "您未通过身份认证，原因为："+noPassReason+"。\n"+"还请核实后再次申请认证。");
                //发送
                mailSender.send(message);
            }
        }).start();

        return "success";
    }
}
