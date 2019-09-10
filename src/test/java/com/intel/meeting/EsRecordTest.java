package com.intel.meeting;

import com.intel.meeting.po.Record;
import com.intel.meeting.po.es.EsRecord;
import com.intel.meeting.repository.es.EsRecordRepository;
import com.intel.meeting.service.RecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IntelMeetingApplication.class)

public class EsRecordTest {

    @Autowired
    private RecordService recordService;
    @Autowired
    private EsRecordRepository esRecordRepository;

    @Test
    public void test(){
        esRecordRepository.deleteAll();
        List<Record> recordList = recordService.findAll();
        EsRecord esRecord = new EsRecord(null,null,null,null,null,null,null);
        for (Record record:recordList){
            esRecord.setRecordId(record.getRecordId());
            esRecord.setUsername(record.getUsername());
            esRecord.setMeetingAddress(record.getMeetingAddress());
            esRecord.setStartTime(record.getStartTime());
            esRecord.setEndTime(record.getEndTime());
            esRecord.setSignTime(record.getSignTime());
            esRecord.setUsageStatus(record.getUsageStatus());
            esRecordRepository.save(esRecord);
        }
    }

    @Test
    public  void testA(){
        List<EsRecord> esRecordList = esRecordRepository.findDistinctByUsernameContainingOrMeetingAddressContaining("zlw","zlw");
        for(EsRecord esRecord: esRecordList){
            System.out.println(esRecord);
        }
    }
}
