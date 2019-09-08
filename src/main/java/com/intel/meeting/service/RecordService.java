package com.intel.meeting.service;

import com.intel.meeting.po.Record;
import org.springframework.data.domain.Page;

/**
 * @author ranger
 * @create 2019-09 -03-11:27
 */
public interface RecordService {
    String saveRecord(Record record);

    Page<Record> findRecordByPage(Integer page, int size);
}
