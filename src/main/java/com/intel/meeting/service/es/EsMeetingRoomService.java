package com.intel.meeting.service.es;

import com.intel.meeting.po.es.EsMeetingRoom;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-02 19:05
 */
public interface EsMeetingRoomService {
    List<EsMeetingRoom> findEsMeetingRoomByMeetingName(String meetingName);

    void save(EsMeetingRoom emr);

    void delEsMeetingRoomById(Integer mrId);
}
