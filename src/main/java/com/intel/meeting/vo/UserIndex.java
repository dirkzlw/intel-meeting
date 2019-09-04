package com.intel.meeting.vo;

/**
 * 用于展示在主页上的博客vo
 * @author Ranger
 * @create 2019-06-04 17:20
 */
public class UserIndex {

    private Integer userId;
    private String headImgUrl;

    protected UserIndex() {
    }

    public UserIndex(Integer userId, String headImgUrl) {
        this.userId = userId;
        this.headImgUrl = headImgUrl;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserIndex{" +
                "userId=" + userId +
                ", headImgUrl='" + headImgUrl + '\'' +
                '}';
    }
}
