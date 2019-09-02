package com.intel.meeting.repository.es;

import com.intel.meeting.po.es.EsMeetingRoom;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Ranger
 * @create 2019-09-02 19:03
 */
public interface EsMeetingRoomRepository extends ElasticsearchRepository<EsMeetingRoom, Integer> {

    List<EsMeetingRoom> findDistinctByMeetingNameContainingOrEnableStatusContaining(String mn, String enable, Sort sort);
}
