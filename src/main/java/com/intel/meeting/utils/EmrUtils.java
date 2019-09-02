package com.intel.meeting.utils;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.es.EsMeetingRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-02 19:38
 */
public class EmrUtils {

    public static List<MeetingRoom> emrListToMrList(List<EsMeetingRoom> emrList){
        List<MeetingRoom> mrList = new ArrayList<>();

        for (EsMeetingRoom emr : emrList) {
            MeetingRoom mr = new MeetingRoom(emr.getMeetingName(),
                    emr.getContainNum(),
                    emr.getEnableStatus());
            mr.setMeetingId(emr.getMeetingId());
            mrList.add(mr);
        }

        return mrList;
    }

}
