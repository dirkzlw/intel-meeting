package com.intel.meeting.vo;

import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.po.Role;
import com.intel.meeting.po.UserAuth;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Set;

public class UserInfo {
    private Integer userId;
//    private String password;
    private String username;
    private String email;
    private String role;
    private String rtn;

    public UserInfo(String rtn) {
        this.rtn = rtn;
    }

    public UserInfo(String username ,String email, String role) {
        this.username = username;
//        this.password=password;
        this.email = email;
        this.role = role;
    }

    public UserInfo(String username,String email, String role,String rtn){
        this.username = username;
//        this.password=password;
        this.email = email;
        this.role = role;
        this.rtn=rtn;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getRtn() {
        return rtn;
    }

    public void setRtn(String rtn) {
        this.rtn = rtn;
    }
}
