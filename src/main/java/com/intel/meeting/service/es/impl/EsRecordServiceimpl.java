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

    /**
     * 根据会议室或者用户名进行查询
     * @param meetingName
     * @return
     */
    @Override
    public List<EsRecord> findDistinctByRealnameContainingOrMeetingAddressContaining(String meetingName) {
        return esRecordRepository.findDistinctByRealnameContainingOrMeetingAddressContaining(meetingName,
                meetingName);
    }
    /**
     * 保存记录
     */
    @Override
    public void save(EsRecord esRecord){
        esRecordRepository.save(esRecord);
    }


}
