package com.intel.meeting.po.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;

/**
 * @author Ranger
 * @create 2019-09-02 18:53
 */
@Document(indexName = "meeting_room_index",type = "meeting_room")
public class EsMeetingRoom {
    //会议室ID
    @Id
    private Integer meetingId;
    //会议室名称
    private String meetingName;
    //容纳人数
    private Integer containNum;
    // 是否可用  1--可用  2--故障
    private String enableStatus;

    protected EsMeetingRoom() {
    }

    public EsMeetingRoom(Integer meetingId, String meetingName, Integer containNum, String enableStatus) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.containNum = containNum;
        this.enableStatus = enableStatus;
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

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    @Override
    public String toString() {
        return "EsMeetingRoom{" +
                "meetingId=" + meetingId +
                ", meetingName='" + meetingName + '\'' +
                ", containNum=" + containNum +
                ", enableStatus='" + enableStatus + '\'' +
                '}';
    }
}
