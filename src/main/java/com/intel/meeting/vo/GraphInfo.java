package com.intel.meeting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Ranger
 * @create 2019-09-10 8:26
 */
@Getter
@Setter
@ToString
public class GraphInfo implements Serializable {

    private String useRate;
    private String signRate;
    private Long week1;
    private Long week2;
    private Long week3;
    private Long week4;
    private Long week5;
    private Long week6;
    private Long week0;

    protected GraphInfo() {
    }

    public GraphInfo(String useRate,
                     String signRate,
                     Long week1,
                     Long week2,
                     Long week3,
                     Long week4,
                     Long week5,
                     Long week6,
                     Long week0) {
        this.useRate = useRate;
        this.signRate = signRate;
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.week5 = week5;
        this.week6 = week6;
        this.week0 = week0;
    }
}
