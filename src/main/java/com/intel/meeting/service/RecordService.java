package com.intel.meeting.service;

import com.intel.meeting.po.Record;
import com.intel.meeting.vo.GraphInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-11:27
 */
public interface RecordService {
    String saveRecord(Record record);

    Page<Record> findRecordByPage(Integer page, int size);

    String getUaeRage();

    String getSignRate();

    long getReserveNumOfWeek(int i);

    GraphInfo getGraphInfo();


    List<Record> findAll();
}
