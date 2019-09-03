package com.intel.meeting.service.impl;

import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.repository.ReserveMeetingRepository;
import com.intel.meeting.service.ReserveMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ranger
 * @create 2019-09 -03-11:20
 */
@Service
public class ReserveMeetingServiceImpl implements ReserveMeetingService {

    @Autowired
    private ReserveMeetingRepository rmRepository;

    /**
     * 保存预定
     * 没有判定冲突
     * @param reserveMeeting
     * @return
     */
    @Override
    public String save(ReserveMeeting reserveMeeting) {

        rmRepository.save(reserveMeeting);

        return "reserve";
    }
}
