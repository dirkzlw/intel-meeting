package com.intel.meeting.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ranger
 * @create 2019-09-04 22:09
 */
@Getter
@Setter
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

}
