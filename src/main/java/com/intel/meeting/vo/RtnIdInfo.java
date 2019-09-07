package com.intel.meeting.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 添加会议室返回的结果信息
 * @author Ranger
 * @create 2019-09-02 8:49
 */
@Getter
@Setter
public class RtnIdInfo {
    private String rtn;
    private Integer rtnId;

    protected RtnIdInfo() {
    }

    public RtnIdInfo(String rtn, Integer rtnId) {
        this.rtn = rtn;
        this.rtnId = rtnId;
    }

}
