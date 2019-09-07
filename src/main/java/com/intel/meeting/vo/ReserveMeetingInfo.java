package com.intel.meeting.vo;

import com.intel.meeting.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ranger
 * @create 2019-09-05 16:08
 */
@Getter
@Setter
public class ReserveMeetingInfo implements Comparable<ReserveMeetingInfo> {
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

    /**
     * List 排序
     * 根据预约日期排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(ReserveMeetingInfo o) {
        long l1 = DateUtils.stringToTime(this.getReserveDay() + " " + this.getStartTime());
        long l2 = DateUtils.stringToTime(o.getReserveDay() + " " + o.getStartTime());
        return (int) ((int) l1 - l2);
    }
}
