package com.intel.meeting.po.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "record_index",type = "es_record")
@Getter
@Setter
public class EsRecord {
    @Id
    private Integer recordId;
    private String meetingAddress;
    private String realname;
    private String startTime;
    private String endTime;
    private String signTime;
    private Integer usageStatus;

    protected EsRecord(){}

    public EsRecord(Integer recordId, String meetingAddress, String realname, String startTime, String endTime, String signTime, Integer usageStatus) {
        this.recordId = recordId;
        this.meetingAddress = meetingAddress;
        this.realname = realname;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signTime = signTime;
        this.usageStatus = usageStatus;
    }
}
