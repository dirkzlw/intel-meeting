package com.intel.meeting.service;

import com.intel.meeting.po.MeetingRoom;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-01 21:34
 */
public interface MeetingRoomService {

    String saveMeetingRoom(MeetingRoom mr);

    List<MeetingRoom> findAllMeetingRoom();

    String delMeetingRoom(Integer meetingId);
}
