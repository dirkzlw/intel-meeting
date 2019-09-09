package com.intel.meeting.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserPage<T> {
    private List<T> userList;
    private Integer page;
    private Integer totalPage;
    private Integer totalNum;
    private Integer isSearch;

    protected UserPage() {
    }

    public UserPage(List<T> userList, Integer page, Integer totalPage, Integer totalNum,Integer isSearch) {
        this.userList = userList;
        this.page = page;
        this.totalPage = totalPage;
        this.totalNum = totalNum;
        this.isSearch = isSearch;
    }
}
