package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jack Wen.
 * @ClassName Goods
 * @dsecription 商品SPU表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Goods implements Serializable {

    private Long id;

    /** 商家ID*/
    private String sellerId;

    /** 商品名称，SPU名称*/
    private String goodsName;

    /** 默认SKU*/
    private Long defaultItemId;

    /** 状态（0 未审核，1 已审核，2 审核未通过，3 关闭）*/
    private String auditStatus;

    /** 是否上架 （0 否，1 是）*/
    private String isMarketable;

    /** 品牌ID*/
    private Long brandId;

    /** 副标题*/
    private String caption;

    /** 一级分类*/
    private Long category1Id;

    /** 二级分类*/
    private Long category2Id;

    /** 三级分类*/
    private Long category3Id;

    /** 小图*/
    private String smallPic;

    /** 价格*/
    private BigDecimal price;

    /** 模板ID*/
    private Long typeTemplateId;

    /** 是否启用规格 （0 否，1 是）*/
    private String isEnableSpec;

    /** 是否删除 （0 否，1 是）*/
    private String isDelete;

    private static final long serialVersionUID = 1L;

}