package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName Areas
 * @dsecription 行政区域县区信息表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Areas implements Serializable {

    private static final long serialVersionUID = 813845492648141256L;

    private Integer id;

    /** 区域ID*/
    private String areaid;

    /** 区域名称*/
    private String area;

    /** 城市ID*/
    private String cityid;

}