package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName Content
 * @dsecription 内容表（广告）
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Content implements Serializable {

    /** ID*/
    private Long id;

    /** 内容分类ID*/
    private Long categoryId;

    /** 内容标题*/
    private String title;

    /** 链接*/
    private String url;

    /** 图片地址*/
    private String pic;

    /** 状态*/
    private String status;

    /** 排序*/
    private Integer sortOrder;

    private static final long serialVersionUID = -6483787315586604908L;

}