package com.intel.meeting.service.impl;

import com.intel.meeting.po.Record;
import com.intel.meeting.repository.RecordRepository;
import com.intel.meeting.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ranger
 * @create 2019-09 -03-11:28
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    /**
     * 保存预定记录
     * @param record
     * @return
     */
    @Override
    public String saveRecord(Record record) {
        try {
            recordRepository.save(record);
        }catch (Exception e){
            return "fail";
        }
        return "success";
    }
}
