package com.intel.meeting.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author ranger
 * @create 2019-09 -03-11:21
 */
@Entity
@Table(name="t_record")
@Getter
@Setter
@ToString
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

}
