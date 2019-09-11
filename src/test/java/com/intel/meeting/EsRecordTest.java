package com.intel.meeting;

import com.intel.meeting.po.Record;
import com.intel.meeting.po.es.EsRecord;
import com.intel.meeting.repository.es.EsRecordRepository;
import com.intel.meeting.service.RecordService;
import com.intel.meeting.service.es.EsRecordService;
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
    @Autowired
    private EsRecordService esRecordService;
    @Test
    public void test(){
        esRecordRepository.deleteAll();
        List<Record> recordList = recordService.findAll();
        EsRecord esRecord = new EsRecord(null,null,null,null,null,null,null);
        for (Record record:recordList){
            esRecord.setRecordId(record.getRecordId());
            esRecord.setRealname(record.getRealname());
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
        List<EsRecord> esRecordList = esRecordRepository.findDistinctByRealnameContainingOrMeetingAddressContaining("zlw","zlw");
        for(EsRecord esRecord: esRecordList){
            System.out.println(esRecord);
        }
    }
}
