package com.intel.meeting.service.impl;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.repository.MeetingRoomRepository;
import com.intel.meeting.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09-01 21:35
 */
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomRepository mrRepository;

    /**
     * 添加会议室
     *
     * @param mr 会议室
     * @return 执行结果
     */
    @Override
    public String saveMeetingRoom(MeetingRoom mr) {
        List<MeetingRoom> mrList = mrRepository.findDistinctByMeetingName(mr.getMeetingName());
        //添加会议室
        if (mr.getMeetingId() == null || "".equals(mr.getMeetingId())) {
            //1. 判断会议室是否存在
            if (mrList.size() == 0) {
                // 不存在
                mrRepository.save(mr);
                return "save";
            } else {
                return "exist";
            }
        } else {
            //修改会议室
            for (MeetingRoom m : mrList) {
                //如果有会议室名称重复情况
                if (m.getMeetingName().equals(mr.getMeetingName())) {
                    //当前会议室修改其他信息
                    if (m.getMeetingId() == mr.getMeetingId()) {
                        mrRepository.save(mr);
                        return "save";
                    } else {
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
     *
     * @return 会议室集合
     */
    @Override
    public List<MeetingRoom> findAllMeetingRoom() {
        return mrRepository.findAll();
    }

    /**
     * 根据id删除会议室
     *
     * @param meetingId 会议室id
     * @return 执行结果
     */
    @Override
    public String delMeetingRoom(Integer meetingId) {
        mrRepository.delete(meetingId);
        return "del";
    }

    /**
     * 分页查询
     *
     * @param page 当前页
     * @param size 每页显示的数据
     * @return 分页对象
     */
    @Override
    public Page<MeetingRoom> findMeetingRoomByPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "meetingName"));
        Page<MeetingRoom> mrPage = mrRepository.findAll(pageable);
        return mrPage;
    }

    /**
     * 根据id查询会议室
     *
     * @param mrId 会议室id
     * @return 会议室
     */
    @Override
    public MeetingRoom findMrById(Integer mrId) {
        return mrRepository.findOne(mrId);
    }

    /**
     * 统计会议室各状态的数量
     *
     * @param status 会议室状态
     * @return 统计的数目
     */
    @Override
    public int countByEnableStatus(String status) {
        return mrRepository.countByEnableStatus(status);
    }

    /**
     * 根据会议室名称模糊查询
     *
     * @param meetingName 会议室名称
     * @return 会议室集合
     */
    @Override
    public List<MeetingRoom> findMeetingRoomLikeName(String meetingName) {
        return mrRepository.findByMeetingNameLikeAndEnableStatus(meetingName, "空闲");
    }
}
