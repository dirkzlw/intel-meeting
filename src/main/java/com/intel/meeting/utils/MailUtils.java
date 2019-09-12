package com.intel.meeting.utils;

import org.springframework.mail.SimpleMailMessage;

/**
 * 发送邮件
 *
 * @author Intel-Meeting
 * @create 2019-09-06 14:53
 */
public class MailUtils {

    /**
     * 发送邮件
     *
     * @param fromEmail 发送者
     * @param email     接收者
     * @param subject   主题
     * @param text      文本
     * @return 返回SimpleMailMessage对象
     */
    public static SimpleMailMessage getMailMessage(String fromEmail, String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(email);

        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}
