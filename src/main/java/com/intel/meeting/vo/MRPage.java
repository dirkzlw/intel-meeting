package com.intel.meeting.vo;

import com.intel.meeting.po.MeetingRoom;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-02 15:59
 */
public class MRPage<T> {
    private List<T> mrList;
    private Integer page;
    private Integer totalPage;
    private Integer totalNum;
    private Integer isSearch;

    protected MRPage() {
    }

    public MRPage(List<T> mrList, Integer page, Integer totalPage, Integer totalNum,Integer isSearch) {
        this.mrList = mrList;
        this.page = page;
        this.totalPage = totalPage;
        this.totalNum = totalNum;
        this.isSearch = isSearch;
    }

    public List<T> getMrList() {
        return mrList;
    }

    public void setMrList(List<T> mrList) {
        this.mrList = mrList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }
}
