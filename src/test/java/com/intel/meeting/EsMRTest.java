package com.intel.meeting;

import com.intel.meeting.po.MeetingRoom;
import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsMeetingRoom;
import com.intel.meeting.po.es.EsUser;
import com.intel.meeting.repository.es.EsMeetingRoomRepository;
import com.intel.meeting.repository.es.EsUserRepository;
import com.intel.meeting.service.MeetingRoomService;
import com.intel.meeting.service.UserService;
import com.intel.meeting.service.es.EsMeetingRoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09-02 19:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IntelMeetingApplication.class)
public class EsMRTest {
    @Autowired
    private MeetingRoomService mrService;
    @Autowired
    private EsMeetingRoomRepository emrRepository;


//    @Before
    public void testB(){
        emrRepository.deleteAll();
        List<MeetingRoom> mrList = mrService.findAllMeetingRoom();
        EsMeetingRoom emr = new EsMeetingRoom(null,
                null
                , null
                , null);
        for (MeetingRoom mr : mrList) {
            emr.setMeetingId(mr.getMeetingId());
            emr.setMeetingName(mr.getMeetingName());
            emr.setContainNum(mr.getContainNum());
            emr.setEnableStatus(mr.getEnableStatus());
            emrRepository.save(emr);
        }
    }

    @Test
    public void testS(){
        List<EsMeetingRoom> bList = emrRepository.findDistinctByMeetingNameContainingOrEnableStatusContaining("故障","故障",null);
        System.out.println("bList.size() = " + bList.size());
        for (EsMeetingRoom emr : bList) {
            System.out.println("emr = " + emr);
        }
    }

}
