package com.intel.meeting.vo;

import java.io.Serializable;

/**
 * @author Ranger
 * @create 2019-09-04 22:09
 */
public class SessionUser implements Serializable {
    //用户ID
    private Integer userId;
    //用户名
    private String username;
    // 角色
    private String role;
    // 头像URL
    private String headUrl;

    protected SessionUser() {
    }

    public SessionUser(Integer userId, String username, String role, String headUrl) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.headUrl = headUrl;
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

    @Override
    public String toString() {
        return "SessionUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
