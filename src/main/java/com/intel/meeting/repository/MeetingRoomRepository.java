package com.intel.meeting.repository;

import com.intel.meeting.po.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-01 21:33
 */
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
    List<MeetingRoom> findDistinctByMeetingName(String meetingName);
}
