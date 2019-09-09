package com.intel.meeting.po;

import com.intel.meeting.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author ranger
 * @create 2019-09 -03-10:58
 */
@Entity
@Table(name="reserve_meeting")
@Getter
@Setter
public class ReserveMeeting implements Comparable<ReserveMeeting>{
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

    /**
     * 根据预约时间排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(ReserveMeeting o) {
        long l1 = DateUtils.stringToTime(this.getStartTime());
        long l2 = DateUtils.stringToTime(o.getStartTime());
        return (int) ((int) l1 - l2);
    }
}
