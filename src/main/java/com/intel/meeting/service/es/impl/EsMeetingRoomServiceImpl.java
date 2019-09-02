package com.intel.meeting.service.es.impl;

import com.intel.meeting.po.es.EsMeetingRoom;
import com.intel.meeting.repository.es.EsMeetingRoomRepository;
import com.intel.meeting.service.es.EsMeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-02 19:08
 */
@Service
public class EsMeetingRoomServiceImpl implements EsMeetingRoomService {

    @Autowired
    private EsMeetingRoomRepository emrRepository;

    @Override
    public List<EsMeetingRoom> findEsMeetingRoomByMeetingName(String meetingName) {
        return emrRepository.findDistinctByMeetingNameContainingOrEnableStatusContaining(meetingName,
                meetingName,
                new Sort(Sort.Direction.ASC,"meetingName"));
    }

    @Override
    public void save(EsMeetingRoom emr) {
        emrRepository.save(emr);
    }

    @Override
    public void delEsMeetingRoomById(Integer mrId) {
        emrRepository.delete(mrId);
    }
}
