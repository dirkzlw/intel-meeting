package com.intel.meeting.repository;

import com.intel.meeting.po.Record;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Intel-Meeting
 * @create 2019-09 -03-11:26
 */
public interface RecordRepository extends JpaRepository<Record, Integer> {
    long countByUsageStatus(Integer usageStatus);

    long countBySignTime(String signTIme);

    long countByWeek(Integer weekNum);
}
