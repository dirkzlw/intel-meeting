package com.intel.meeting.service.es;

import com.intel.meeting.po.es.EsRecord;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EsRecordService {
    List<EsRecord> findDistinctByRealnameContainingOrMeetingAddressContaining(String meetingName);

    void save(EsRecord esRecord);

}
