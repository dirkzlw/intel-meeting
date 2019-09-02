package com.intel.meeting.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ranger
 * @create 2019-09-01 20:51
 */
@Entity
@Table(name="meeting_room")
public class MeetingRoom {

    //会议室ID
    @Id
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer meetingId;

    //会议室名称
    @Column(length = 30)
    private String meetingName;

    //容纳人数
    @Column(length = 20)
    private Integer containNum;

    // 是否可用  1--可用  2--故障
    @Column(length = 20)
    private String enableStatus;

    public Integer getMeetingId() {
        return meetingId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public Integer getContainNum() {
        return containNum;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public void setContainNum(Integer containNum) {
        this.containNum = containNum;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "meetingId=" + meetingId +
                ", containNum=" + containNum +
                ", meetingName='" + meetingName + '\'' +
                ", enableStatus=" + enableStatus +
                '}';
    }
}
