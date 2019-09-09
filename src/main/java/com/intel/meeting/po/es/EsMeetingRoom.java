package com.intel.meeting.po.es;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;

/**
 * @author Ranger
 * @create 2019-09-02 18:53
 */
@Document(indexName = "meeting_room_index",type = "meeting_room")
@Getter
@Setter
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
}
