package com.intel.meeting.po.es;

import com.intel.meeting.po.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user_index",type = "es_user")
@Getter
@Setter
public class EsUser {
    @Id
    private Integer userId;
    private String username;
    private String email;
    private String role;

    protected EsUser(){}

    public EsUser(Integer userId, String username, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
