package com.intel.meeting.po;

import javax.persistence.*;

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
    //用户
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    //会议室
    @ManyToOne
    @JoinColumn(name = "meetingId")
    private MeetingRoom meetingRoom;
    // 开始时间
    @Column(length = 40)
    private String startTime;
    // 结束时间
    @Column(length = 40)
    private String endTime;
    // 使用状态  1--使用  2--取消
    @Column(length = 10)
    private Integer usageStatus;
    // 签到时间
    @Column(length = 40)
    private String signTime;

    protected ReserveMeeting() {
    }

    public ReserveMeeting(User user, MeetingRoom meetingRoom, String startTime, String endTime, Integer usageStatus, String signTime) {
        this.user = user;
        this.meetingRoom = meetingRoom;
        this.startTime = startTime;
        this.endTime = endTime;
        this.usageStatus = usageStatus;
        this.signTime = signTime;
    }

    public Integer getReserveId() {
        return reserveId;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getUsageStatus() {
        return usageStatus;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public void setUsageStatus(Integer usageStatus) {
        this.usageStatus = usageStatus;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public void setReserveId(Integer reserveId) {
        this.reserveId = reserveId;
    }

    @Override
    public String toString() {
        return "ReserveMeeting{" +
                "reserveId=" + reserveId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", usageStatus=" + usageStatus +
                ", signTime=" + signTime +
                '}';
    }
}
