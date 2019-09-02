package com.intel.meeting.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Ranger
 * @create 2019-09-02 22:26
 */
@Entity
@Table(name="t_user")
public class User {
    //用户ID
    @Id
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    //用户名
    @Column(length = 30)
    private String username;
    //密码
    @Column(length = 30)
    private String password;
    // 邮箱
    @Column(length = 40)
    private String email;
    // 角色
    @Column(length = 20)
    private String role;
    // 头像URL
    @Column(length = 100)
    private String headUrl;
    // 账户状态 1-可用 2-黑名单
    @Column(length = 10)
    private Integer status;
    // 警告次数  5次禁用三天
    @Column(length = 10)
    private Integer warnNum;
    // 封号截止时间
    private Date untilTime;
    // 认证id
    @Column(length = 10)
    private Integer authId;

    protected User(){
    }

    public User(String username, String password, String email, String role, String headUrl, Integer status, Integer warnNum, Date untilTime, Integer authId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.headUrl = headUrl;
        this.status = status;
        this.warnNum = warnNum;
        this.untilTime = untilTime;
        this.authId = authId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(Integer warnNum) {
        this.warnNum = warnNum;
    }

    public Date getUntilTime() {
        return untilTime;
    }

    public void setUntilTime(Date untilTime) {
        this.untilTime = untilTime;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", status=" + status +
                ", warnNum=" + warnNum +
                ", untilTime=" + untilTime +
                ", authId=" + authId +
                '}';
    }
}
