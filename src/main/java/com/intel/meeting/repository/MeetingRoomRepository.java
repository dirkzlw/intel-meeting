package com.intel.meeting.repository;

import com.intel.meeting.po.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09-01 21:33
 */
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
    List<MeetingRoom> findDistinctByMeetingName(String meetingName);

    int countByEnableStatus(String status);

//    @Query(value="select * from meeting_room where meeting_name like ?",nativeQuery=true)
    List<MeetingRoom> findByMeetingNameLikeAndEnableStatus(String name,String status);
}
