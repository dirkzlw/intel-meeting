package com.intel.meeting.utils;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author Ranger
 * @create 2019-09-06 14:53
 */
public class MailUtils {

    public static SimpleMailMessage getMailMessage(String fromEmail,String email,String subject,String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(email);

        message.setSubject(subject);
        message.setText(text);

        return message;
    }

}
