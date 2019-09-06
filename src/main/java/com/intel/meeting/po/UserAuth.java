package com.intel.meeting.po;

import javax.persistence.*;

/**
 * @author ranger
 * @create 2019-09 -03-10:41
 */
@Entity
@Table(name="user_auth")
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

    public UserAuth(){
    }

    public UserAuth(String jobNum,String realName, String authUrl) {
        this.jobNum = jobNum;
        this.realName = realName;
        this.authUrl = authUrl;
    }

    public Integer getAuthId() {
        return authId;
    }

    public String getRealName() {
        return realName;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }


    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "authId=" + authId +
                ", realName='" + realName + '\'' +
                ", jobNum='" + jobNum + '\'' +
                ", authUrl='" + authUrl + '\'' +
                '}';
    }
}
