package com.intel.meeting.config;

import com.intel.meeting.po.Record;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsRecord;
import com.intel.meeting.service.RecordService;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.service.es.EsRecordService;
import com.intel.meeting.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 定时清理已结束的会议室预约reserve_meeting
 *
 * @author Intel-Meeting
 * @create 2019-09-07 10:54
 */
@Component
public class RecordConfig {

    @Autowired
    private ReserveMeetingService rmService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EsRecordService esRecordService;

    @Value("${INIT_SIGN_TIME}")
    private String INIT_SIGN_TIME;

    /**
     * 定时任务
     * 用来处理正常结束的会议，将预约表数据迁移至记录表
     * 测试：每2分钟钟执行一次
     * 上线：每3小时执行一次
     */
//    @Scheduled(cron = "0 */2 * * * ?")
    @Scheduled(cron = "0 0 */3 * * ?")
    public void cleanReserveMeeting() {
        List<ReserveMeeting> rmList = rmService.finAllReserveMeeting();
        for (ReserveMeeting rm : rmList) {
            long endTime = DateUtils.stringToTime(rm.getEndTime());
            long nowTime = new Date().getTime();
            if (nowTime > endTime) {
                User reserveUser = rm.getUser();
                Record record = new Record(reserveUser.getUserAuth().getRealname(),
                        rm.getMeetingRoom().getMeetingName(),
                        rm.getStartTime(),
                        rm.getEndTime(),
                        rm.getSignTime(),
                        rm.getUsageStatus(),
                        DateUtils.getWeekOfTime(rm.getStartTime()));
                if (INIT_SIGN_TIME.equals(rm.getSignTime())) {
                    //没有签到
                    userService.doNoSign(reserveUser);
                }
                //保存记录，删除预定
                recordService.saveRecord(record);
                EsRecord esRecord = new EsRecord(record.getRecordId(),
                        rm.getMeetingRoom().getMeetingName(),
                        reserveUser.getUsername(),
                        rm.getStartTime(),
                        rm.getEndTime(),
                        rm.getSignTime(),
                        rm.getUsageStatus());
                esRecordService.save(esRecord);
                rmService.delReserveMeetingById(rm.getReserveId());
            }
        }
        //清除redis上的缓存
        redisTemplate.delete("graphRedis");
    }
}
