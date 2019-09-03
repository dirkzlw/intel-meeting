package com.intel.meeting.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ranger
 * @create 2019-09 -03-10:58
 */
@Entity
@Table(name="reserve_meeting")
public class ReserveMeeting {
    //预订ID
    @Id
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reserveId;
    //用户ID
    @Column(length = 10)
    private Integer userId;
    //会议室ID
    @Column(length = 10)
    private Integer meetingroomId;
    // 开始时间
    @Column(length = 40)
    private Date startTime;
    // 结束时间
    @Column(length = 40)
    private Date endTime;
    // 使用状态
    @Column(length = 10)
    private Integer usageStatus;
    // 签到时间
    @Column(length = 40)
    private Date signTime;

    public ReserveMeeting() {
    }

    public ReserveMeeting(Integer userId,Integer meetingroomId, Date startTime, Date endTime, Date signTime, Integer usageStatus) {
        this.userId = userId;
        this.meetingroomId = meetingroomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signTime = signTime;
        this.usageStatus = usageStatus;
    }

    public Integer getReserveId() {
        return reserveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getMeetingroomId() {
        return meetingroomId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getUsageStatus() {
        return usageStatus;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public void setUsageStatus(Integer usageStatus) {
        this.usageStatus = usageStatus;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setMeetingroomId(Integer meetingroomId) {
        this.meetingroomId = meetingroomId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setReserveId(Integer reserveId) {
        this.reserveId = reserveId;
    }

    @Override
    public String toString() {
        return "ReserveMeeting{" +
                "reserveId=" + reserveId +
                ", userId=" + userId +
                ", meetingroomId=" + meetingroomId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", usageStatus=" + usageStatus +
                ", signTime=" + signTime +
                '}';
    }
}
