package com.intel.meeting.vo;

/**
 * @author Ranger
 * @create 2019-09-05 16:08
 */
public class ReserveMeetingInfo implements Comparable<ReserveMeetingInfo>{
    private Integer reserveId;
    private Integer userId;
    private Integer meetingId;
    private String meetingName;
    private Integer containNum;
    private String reserveDay;
    private String startTime;
    private String endTime;
    //1--已签到  2--未签到
    private Integer signStatus;
    //1--已结束  2--未结束
    private Integer endStatus;

    protected ReserveMeetingInfo() {
    }

    public ReserveMeetingInfo(Integer reserveId,
                              Integer userId,
                              Integer meetingId,
                              String meetingName,
                              Integer containNum,
                              String reserveDay,
                              String startTime,
                              String endTime,
                              Integer signStatus,
                              Integer endStatus) {
        this.reserveId = reserveId;
        this.userId = userId;
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.containNum = containNum;
        this.reserveDay = reserveDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signStatus = signStatus;
        this.endStatus = endStatus;
    }

    public Integer getReserveId() {
        return reserveId;
    }

    public void setReserveId(Integer reserveId) {
        this.reserveId = reserveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getReserveDay() {
        return reserveDay;
    }

    public void setReserveDay(String reserveDay) {
        this.reserveDay = reserveDay;
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

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Integer getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(Integer endStatus) {
        this.endStatus = endStatus;
    }

    @Override
    public int compareTo(ReserveMeetingInfo o) {
        return this.meetingId - o.meetingId;
    }
}
