package com.intel.meeting.repository.es;

import com.intel.meeting.po.User;
import com.intel.meeting.po.es.EsMeetingRoom;
import com.intel.meeting.po.es.EsUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsUserRepository extends ElasticsearchRepository<EsUser, Integer> {
    List<EsUser> findByUsernameContaining(String username);

}
