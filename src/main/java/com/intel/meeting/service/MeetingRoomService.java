package com.intel.meeting.service;

import com.intel.meeting.po.MeetingRoom;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-01 21:34
 */
public interface MeetingRoomService {

    String saveMeetingRoom(MeetingRoom mr);

    List<MeetingRoom> findAllMeetingRoom();

    String delMeetingRoom(Integer meetingId);

    Page<MeetingRoom> findMeetingRoomByPage(Integer page, Integer size);

    MeetingRoom findMrById(Integer mrId);

    int countByEnableStatus(String status);
}
