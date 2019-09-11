package com.intel.meeting.service.es.impl;

import com.intel.meeting.po.es.EsRecord;
import com.intel.meeting.repository.es.EsRecordRepository;
import com.intel.meeting.service.es.EsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsRecordServiceimpl implements EsRecordService {
    @Autowired
    private EsRecordRepository esRecordRepository;

    @Override
    public List<EsRecord> findDistinctByUsernameContainingOrMeetingNameContaining(String meetingName) {
        return esRecordRepository.findDistinctByUsernameContainingOrMeetingAddressContaining(meetingName,
                meetingName);
    }
}
