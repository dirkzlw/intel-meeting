package com.intel.meeting.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于展示在主页上的博客vo
 * @author Ranger
 * @create 2019-06-04 17:20
 */
@Getter
@Setter
public class UserIndex {

    private Integer userId;
    private String headImgUrl;

    protected UserIndex() {
    }

    public UserIndex(Integer userId, String headImgUrl) {
        this.userId = userId;
        this.headImgUrl = headImgUrl;
    }
}
