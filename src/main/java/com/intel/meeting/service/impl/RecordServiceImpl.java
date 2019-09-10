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
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    @Override
    public GraphInfo getGraphInfo() {
        //重置序列化器--json格式
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(GraphInfo.class));
        GraphInfo graphRedis = (GraphInfo) redisTemplate.opsForValue().get("graphRedis");
        if (graphRedis == null) {
            //统计使用率
            long count = recordRepository.count();
            long countUse = recordRepository.countByUsageStatus(1);
            String useRate = MathUtils.getRate(countUse, count);
            //统计签到数
            long countSign = recordRepository.countBySignTime("0000-00-00 00:00");
            String signRate = MathUtils.getRate(count - countSign, count);
            //统计周？的预定量
            long week1 = recordRepository.countByWeek(1);
            long week2 = recordRepository.countByWeek(2);
            long week3 = recordRepository.countByWeek(3);
            long week4 = recordRepository.countByWeek(4);
            long week5 = recordRepository.countByWeek(5);
            long week6 = recordRepository.countByWeek(6);
            long week0 = recordRepository.countByWeek(0);
            //创建结果对象
            graphRedis = new GraphInfo(useRate,
                    signRate, week1, week2, week3,
                    week4, week5, week6, week0);
            //将结果对象存储在redis
            redisTemplate.opsForValue().set("graphRedis", graphRedis);
        }
        return graphRedis;
    }
    /**
     * 获取全部记录
     */
    public List<Record> findAll(){
       return recordRepository.findAll();
    }

}
