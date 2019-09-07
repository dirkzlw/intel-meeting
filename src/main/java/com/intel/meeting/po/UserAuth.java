package com.intel.meeting.po;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author ranger
 * @create 2019-09 -03-10:41
 */
@Entity
@Table(name="user_auth")
@Getter
@Setter
@ToString
public class UserAuth {
    //认证ID
    @Id
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;
    //姓名
    @Column(length = 30)
    private String realname;
    //工号
    @Column(length = 20)
    private String jobNum;
    // 认证资质Url
    @Column(length = 100)
    private String authUrl;
    // 状态 1--通过  2--审核  3--未通过
    @Column(length = 10)
    private Integer authStatus;
    @OneToOne(mappedBy = "userAuth")
    private User user;

    protected UserAuth(){
    }

    public UserAuth(String realname, String jobNum, String authUrl, Integer authStatus, User user) {
        this.realname = realname;
        this.jobNum = jobNum;
        this.authUrl = authUrl;
        this.authStatus = authStatus;
        this.user = user;
    }
}
