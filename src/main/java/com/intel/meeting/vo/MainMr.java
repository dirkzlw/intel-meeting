package com.intel.meeting.vo;

import com.intel.meeting.po.ReserveMeeting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * 主页展示的MR
 *
 * @author Ranger
 * @create 2019-09-03 20:13
 */
@Getter
@Setter
public class MainMr {

    //会议室ID
    private Integer meetingId;
    //会议室名称
    private String meetingName;
    //容纳人数
    private Integer containNum;
    // 预约状态
    private String reserveStatus;
    // 预约时间
    private String reserveTime;
    // 预约返回结果
    private String rtn;

    protected MainMr() {
    }

    public MainMr(String rtn) {
        this.rtn = rtn;
    }

    public MainMr(Integer meetingId,
                  String meetingName,
                  Integer containNum,
                  String reserveStatus,
                  String reserveTime,
                  String rtn) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.containNum = containNum;
        this.reserveStatus = reserveStatus;
        this.reserveTime = reserveTime;
        this.rtn = rtn;
    }

}
