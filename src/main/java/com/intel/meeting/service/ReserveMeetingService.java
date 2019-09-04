package com.intel.meeting.service;

import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.vo.MainMr;

/**
 * @author ranger
 * @create 2019-09 -03-11:19
 */
public interface ReserveMeetingService {
    MainMr save(ReserveMeeting reserveMeeting);
}
