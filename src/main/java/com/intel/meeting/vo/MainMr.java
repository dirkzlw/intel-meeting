package com.intel.meeting.vo;

import com.intel.meeting.po.ReserveMeeting;

import java.util.HashSet;
import java.util.Set;

/**
 * 主页展示的MR
 *
 * @author Ranger
 * @create 2019-09-03 20:13
 */
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

    protected MainMr() {
    }

    public MainMr(Integer meetingId, String meetingName, Integer containNum, String reserveStatus, String reserveTime) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.containNum = containNum;
        this.reserveStatus = reserveStatus;
        this.reserveTime = reserveTime;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public Integer getContainNum() {
        return containNum;
    }

    public void setContainNum(Integer containNum) {
        this.containNum = containNum;
    }

    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }
}
