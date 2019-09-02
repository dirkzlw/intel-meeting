package com.intel.meeting.vo;

/**
 * 添加会议室返回的结果信息
 * @author Ranger
 * @create 2019-09-02 8:49
 */
public class RtnIdInfo {
    private String rtn;
    private Integer rtnId;

    protected RtnIdInfo() {
    }

    public RtnIdInfo(String rtn, Integer rtnId) {
        this.rtn = rtn;
        this.rtnId = rtnId;
    }

    public String getRtn() {
        return rtn;
    }

    public void setRtn(String rtn) {
        this.rtn = rtn;
    }

    public Integer getRtnId() {
        return rtnId;
    }

    public void setRtnId(Integer rtnId) {
        this.rtnId = rtnId;
    }
}
