package com.intel.meeting.repository.es;

import com.intel.meeting.po.es.EsRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsRecordRepository extends ElasticsearchRepository<EsRecord, Integer> {
      List<EsRecord> findDistinctByUsernameContainingOrMeetingNameContaining(String mn, String enable, Sort sort );
}
