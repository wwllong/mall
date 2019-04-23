package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Jack Wen.
 * @ClassName ContentCategory
 * @dsecription 内容分类表（广告）
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class ContentCategory implements Serializable {

    /** 分类ID*/
    private Long id;

    /** 内容/广告分类名称*/
    private String name;

    private static final long serialVersionUID = 8824855248781418619L;
}