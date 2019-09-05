package com.intel.meeting.repository;

import com.intel.meeting.po.ReserveMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-11:11
 */
public interface ReserveMeetingRepository extends JpaRepository<ReserveMeeting, Integer> {
}
