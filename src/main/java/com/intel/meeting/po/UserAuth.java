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
    private String realName;
    //工号
    @Column(length = 20)
    private String jobNum;
    // 认证资质
    @Column(length = 100)
    private String authUrl;

    protected UserAuth(){
    }

    public UserAuth(String jobNum,String realName, String authUrl) {
        this.jobNum = jobNum;
        this.realName = realName;
        this.authUrl = authUrl;
    }

}
