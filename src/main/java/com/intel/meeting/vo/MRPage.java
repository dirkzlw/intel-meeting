package com.intel.meeting.vo;

import com.intel.meeting.po.MeetingRoom;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-02 15:59
 */
@Getter
@Setter
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
}
