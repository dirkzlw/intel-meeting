package com.intel.meeting.service.impl;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.repository.MeetingRoomRepository;
import com.intel.meeting.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-01 21:35
 */
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomRepository mrRepository;

    /**
     * 添加会议室
     * @param mr
     * @return
     */
    @Override
    public String saveMeetingRoom(MeetingRoom mr) {
        List<MeetingRoom> mrList = mrRepository.findDistinctByMeetingName(mr.getMeetingName());
        //添加会议室
        if(mr.getMeetingId() == null || "".equals(mr.getMeetingId())){
            //1. 判断会议室是否存在
            if(mrList.size() == 0){
                // 不存在
                mrRepository.save(mr);
                return "save";
            }else {
                return "exist";
            }
        }else {
            //修改会议室
            for (MeetingRoom m : mrList) {
                //如果有会议室名称重复情况
                if(m.getMeetingName().equals(mr.getMeetingName())){
                    //当前会议室修改其他信息
                    if(m.getMeetingId() == mr.getMeetingId()){
                        mrRepository.save(mr);
                        return "save";
                    }else {
                        //出现重复情况
                        return "exist";
                    }
                }
            }
            mrRepository.save(mr);
            return "save";
        }


    }

    /**
     * 查询所有会议室
     * @return
     */
    @Override
    public List<MeetingRoom> findAllMeetingRoom() {
        return mrRepository.findAll();
    }

    /**
     * 根据id删除会议室
     * @param meetingId
     * @return
     */
    @Override
    public String delMeetingRoom(Integer meetingId) {
        mrRepository.delete(meetingId);
        return "del";
    }
}
