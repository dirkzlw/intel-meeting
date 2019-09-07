package com.intel.meeting.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Integer userId;
    private String username;
    private String email;
    private String role;
    private String rtn;

    public UserInfo(String rtn) {
        this.rtn = rtn;
    }

    public UserInfo(String username ,String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public UserInfo(String username,String email, String role,String rtn){
        this.username = username;
        this.email = email;
        this.role = role;
        this.rtn=rtn;
    }

}
