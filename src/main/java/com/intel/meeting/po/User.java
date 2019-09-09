package com.intel.meeting.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ranger
 * @create 2019-09-02 22:26
 */
@Entity
@Table(name = "t_user")
@Getter
@Setter
@ToString
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
    @Column(length = 40)
    private String password;
    // 邮箱
    @Column(length = 40)
    private String email;
    // 角色
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    // 头像URL
    @Column(length = 100)
    private String headUrl;
    // 警告次数  5次禁用三天
    @Column(length = 10)
    private Integer warnNum;
    // 封号截止时间
    @Column(length = 40)
    private String untilTime;
    // 用户认证
    @OneToOne
    @JoinColumn(name = "authId")
    private UserAuth userAuth;
    // 预定
    @OneToMany(mappedBy = "user")
    private Set<ReserveMeeting> reserveSet = new HashSet<>();

    protected User() {
    }

    public User(String username, String password, String email, Role role, String headUrl, Integer warnNum, String untilTime, UserAuth userAuth, Set<ReserveMeeting> reserveSet) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.headUrl = headUrl;
        this.warnNum = warnNum;
        this.untilTime = untilTime;
        this.userAuth = userAuth;
        this.reserveSet = reserveSet;
    }

    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
