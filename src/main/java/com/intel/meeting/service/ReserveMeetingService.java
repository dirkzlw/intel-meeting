package com.intel.meeting.service;

import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.vo.MainMr;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09 -03-11:19
 */
public interface ReserveMeetingService {
    MainMr save(ReserveMeeting reserveMeeting);

    String cancelReserveMeeting(Integer reserveId);

    String signReserveMeeting(Integer reserveId);

    String overReserveMeeting(Integer reserveId);

    ReserveMeeting findOneById(Integer reserveId);

    void delReserveMeetingById(Integer reserveId);

    List<ReserveMeeting> finAllReserveMeeting();
}
