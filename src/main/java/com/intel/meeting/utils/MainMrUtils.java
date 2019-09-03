package com.intel.meeting.utils;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.vo.MainMr;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换主页显示的List
 *
 * @author Ranger
 * @create 2019-09-03 20:00
 */
public class MainMrUtils {
    public static List<MainMr> mrListToMainMrList(List<MeetingRoom> mrList){
        List<MainMr> mainMrList = new ArrayList<>();

        for (MeetingRoom mr : mrList) {
            MainMr mainMr = new MainMr(mr.getMeetingId(),
                    mr.getMeetingName(),
                    mr.getContainNum(),
                    "情况未确定",
                    "2019-09-03 05:05--06:06"+"<br/>"+"2019-09-03 05:05--06:06");
            mainMrList.add(mainMr);
        }
        return mainMrList;
    }
}
