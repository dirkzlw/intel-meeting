package com.intel.meeting.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ranger
 * @create 2019-09 -03-11:21
 */
@Entity
@Table(name="t_record")
public class Record {
    //记录ID
    @Id
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;
    //预订人
    @Column(length = 20)
    private String userName;
    //预订的会议室
    @Column(length = 30)
    private String meetingAddress;
    // 开始时间
    @Column(length = 40)
    private Date startTime;
    // 结束时间
    @Column(length = 40)
    private Date endTime;
    // 签到时间
    @Column(length = 40)
    private Date signTime;

    public Record(){
    }

    public Record(String userName, String meetingAddress, Date startTime, Date endTime, Date signTime) {
        this.userName = userName;
        this.meetingAddress = meetingAddress;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signTime = signTime;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordId=" + recordId +
                ", userName='" + userName + '\'' +
                ", meetingAddress='" + meetingAddress + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", signTime=" + signTime +
                '}';
    }
}
