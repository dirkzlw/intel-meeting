package com.intel.meeting.service.impl;

import com.intel.meeting.po.Record;
import com.intel.meeting.repository.RecordRepository;
import com.intel.meeting.service.RecordService;
import com.intel.meeting.utils.MailUtils;
import com.intel.meeting.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Record> findRecordByPage(Integer page, int size) {
        Pageable pageable = new PageRequest(page, size );
        Page<Record> recordPage = recordRepository.findAll(pageable);
        return recordPage;
    }

    /**
     * 获取使用率
     * @return
     */
    @Override
    public String getUaeRage() {
        //获取总数
        long count = recordRepository.count();
        //获取使用数
        long countUse = recordRepository.countByUsageStatus(1);

        return MathUtils.getRate(countUse, count);
    }

    /**
     * 获取签到率
     * @return
     */
    @Override
    public String getSignRate() {
        //获取总数
        long count = recordRepository.count();
        //获取签到数
        long countSign = recordRepository.countBySignTime("0000-00-00 00:00");
        return MathUtils.getRate(count-countSign,count );
    }

    /**
     * 获取周几的预约量
     * @param i
     * @return
     */
    @Override
    public long getReserveNumOfWeek(int i) {
        return recordRepository.countByWeek(i);
    }

}
