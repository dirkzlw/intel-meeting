package com.intel.meeting.controller;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.Record;
import com.intel.meeting.po.ReserveMeeting;
import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsRecord;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.RecordService;
import com.intel.meeting.service.ReserveMeetingService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.service.es.EsRecordService;
import com.intel.meeting.utils.DateUtils;
import com.intel.meeting.utils.ReserveMeetingUtiles;
import com.intel.meeting.utils.SessionUtils;
import com.intel.meeting.vo.MainMr;
import com.intel.meeting.vo.ReserveMeetingInfo;
import com.intel.meeting.vo.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 预定会议室
 *
 * @author Ranger
 * @create 2019-09-03 20:53
 */
@Controller
public class ReserveMeetingController {

    @Autowired
    private MeetingRoomService mrService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReserveMeetingService rmService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EsRecordService esRecordService;

    @Value("${INIT_SIGN_TIME}")
    private String INIT_SIGN_TIME;

    /**
     * 跳转至预定中心
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/to/reserve/center")
    public String toReserveCenter(Model model,
                                  HttpServletRequest request) {
        //从session中获取用户
        SessionUser userIndex = (SessionUser) SessionUtils.getObjectFromSession(request, "sessionUser");

        User user = userService.findUserById(userIndex.getUserId());
        Set<ReserveMeeting> reserveSet = user.getReserveSet();
        List<ReserveMeetingInfo> rmiList = ReserveMeetingUtiles.reserveSetToRMList(reserveSet);
        //根据预定顺序排序
        Collections.sort(rmiList);
        model.addAttribute("rmiList", rmiList);
        SessionUtils.setUserIndex(model, request);

        return "user/reserve-center";
    }

    /**
     * 预定会议室
     *
     * @param meetingId  会议室id
     * @param reserveDay 预约日期
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param request
     * @return
     */
    @PostMapping("/meeting/reserve")
    @ResponseBody
    public MainMr reserveMeeting(Integer meetingId,
                                 String reserveDay,
                                 String startTime,
                                 String endTime,
                                 HttpServletRequest request) {
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            return new MainMr("notLogin");
        }
        boolean isBlack = userService.isBlackList(sessionUser.getUserId());
        User user = userService.findUserById(sessionUser.getUserId());
        if (user.getUserAuth() == null) {
            //没有提交认证
            return new MainMr("notAuth");
        } else if (user.getUserAuth().getAuthStatus() == 2) {
            //审核中
            return new MainMr("authIng");
        } else if (user.getUserAuth().getAuthStatus() == 3) {
            //审核不通过
            return new MainMr("authNo");
        }
        if (isBlack) {
            return new MainMr(user.getUntilTime());
        }
        MeetingRoom meetingRoom = mrService.findMrById(meetingId);
        ReserveMeeting reserveMeeting = new ReserveMeeting(user,
                meetingRoom,
                reserveDay + " " + startTime,
                reserveDay + " " + endTime,
                1,
                INIT_SIGN_TIME);

        return rmService.save(reserveMeeting);
    }

    /**
     * 取消预定
     *
     * @param reserveId 预约id
     * @return
     */
    @PostMapping("/meeting/reserve/cancel")
    @ResponseBody
    public String cancelReserveMeeting(Integer reserveId) {
        String result = rmService.cancelReserveMeeting(reserveId);
        //可以取消
        if ("success".equals(result)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rmMoveToRecord(reserveId,
                            rmService,
                            INIT_SIGN_TIME,
                            userService,
                            recordService,
                            true);
                    //清除redis上的缓存
                    redisTemplate.delete("graphRedis");
                }
            }).start();
        }

        return result;
    }

    /**
     * 签到
     *
     * @param reserveId 预约id
     * @return
     */
    @PostMapping("/meeting/reserve/sign")
    @ResponseBody
    public String signReserveMeeting(Integer reserveId) {

        return rmService.signReserveMeeting(reserveId);
    }

    /**
     * 提前结束会议室使用
     *
     * @param reserveId 预约id
     * @return
     */
    @PostMapping("/meeting/reserve/over")
    @ResponseBody
    public String overReserveMeeting(Integer reserveId) {

        String result = rmService.overReserveMeeting(reserveId);
        //创建新线程，以免影响响应
        new Thread(new Runnable() {
            @Override
            public void run() {
                //当成功结束会议时，将reserve_meeting表中信息迁移至t_record;
                if ("success".equals(result)) {
                    rmMoveToRecord(reserveId,
                            rmService,
                            INIT_SIGN_TIME,
                            userService,
                            recordService,
                            false);
                    //清除redis上的缓存
                    redisTemplate.delete("graphRedis");
                }
            }
        }).start();

        return result;
    }

    /**
     * 抽象出来的公用方法
     * 用在提前结束会议和取消会议中
     * 将reserve_meeting数据转移至t_record
     *
     * @param reserveId         会议室id
     * @param rmService         预约表的Service
     * @param INIT_SIGN_TIME    签到时间
     * @param userService       用户表的Service
     * @param recordService     记录表的Service
     */
    private void rmMoveToRecord(Integer reserveId,
                                ReserveMeetingService rmService,
                                String INIT_SIGN_TIME,
                                UserService userService,
                                RecordService recordService,
                                boolean isCancel) {
        //转换对象
        ReserveMeeting reserveMeeting = rmService.findOneById(reserveId);
        //获取预约会议室的用户
        User reserveUser = reserveMeeting.getUser();
        Record record = new Record(reserveUser.getUserAuth().getRealname(),
                reserveMeeting.getMeetingRoom().getMeetingName(),
                reserveMeeting.getStartTime(),
                reserveMeeting.getEndTime(),
                reserveMeeting.getSignTime(),
                reserveMeeting.getUsageStatus(),
                DateUtils.getWeekOfTime(reserveMeeting.getStartTime()));
        if (!isCancel && INIT_SIGN_TIME.equals(reserveMeeting.getSignTime())) {
            //没有签到(除却取消会)
            userService.doNoSign(reserveUser);
        }
        //保存记录，删除预定
        recordService.saveRecord(record);
        EsRecord esRecord = new EsRecord(record.getRecordId(),
                reserveMeeting.getMeetingRoom().getMeetingName(),
                reserveUser.getUsername(),
                reserveMeeting.getStartTime(),
                reserveMeeting.getEndTime(),
                reserveMeeting.getSignTime(),
                reserveMeeting.getUsageStatus());
        esRecordService.save(esRecord);
        rmService.delReserveMeetingById(reserveId);
    }
}
