package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName GoodsDesc
 * @dsecription 商品SPU明细/扩展表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class GoodsDesc implements Serializable {

    /** 商品SPU_ID*/
    private Long goodsId;

    /** 描述*/
    private String introduction;

    /** 规格结果集，所有规格，包含isSelected*/
    private String specificationItems;

    /** 自定义属性（参数结果）*/
    private String customAttributeItems;

    /** 图片*/
    private String itemImages;

    /** 包装列表*/
    private String packageList;

    /** 售后服务*/
    private String saleService;

    private static final long serialVersionUID = 1L;

}