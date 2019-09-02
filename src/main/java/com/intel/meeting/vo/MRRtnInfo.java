package com.intel.meeting.vo;

/**
 * 添加会议室返回的结果信息
 * @author Ranger
 * @create 2019-09-02 8:49
 */
public class MRRtnInfo {
    private String status;
    private Integer meetingId;

    protected MRRtnInfo() {
    }

    public MRRtnInfo(String status, Integer meetingId) {
        this.status = status;
        this.meetingId = meetingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }
}
