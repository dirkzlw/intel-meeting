package com.intel.meeting.po;

import javax.persistence.*;

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
    private String startTime;
    // 结束时间
    @Column(length = 40)
    private String endTime;
    // 签到时间
    @Column(length = 40)
    private String signTime;
    //会议室使用状态  1--使用  2--取消
    private Integer usageStatus;

    protected Record(){
    }


    public Record(String userName, String meetingAddress, String startTime, String endTime, String signTime, Integer usageStatus) {
        this.userName = userName;
        this.meetingAddress = meetingAddress;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signTime = signTime;
        this.usageStatus = usageStatus;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public Integer getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(Integer usageStatus) {
        this.usageStatus = usageStatus;
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
