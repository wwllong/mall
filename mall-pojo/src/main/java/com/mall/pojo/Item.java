package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jack Wen.
 * @ClassName Item
 * @dsecription 商品SKU表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Item implements Serializable {

    private Long id;

    /** 商品标题*/
    private String title;

    /** 商品卖点*/
    private String sellPoint;

    /** 商品价格，元*/
    private BigDecimal price;

    /** 存货数量*/
    private Integer stockCount;

    /** 库存数量*/
    private Integer num;

    /** 商品条形码*/
    private String barcode;

    /** 商品图片，第一张*/
    private String image;

    /** 商品所属类目，叶子目录*/
    private Long categoryId;

    /** 商品状态，(0 未处理 1 正常，2 下架，3 关闭)*/
    private String status;

    /** 创建时间*/
    private Date createTime;

    /** 更新时间*/
    private Date updateTime;

    /** */
    private String itemSn;

    /** 成本价*/
    private BigDecimal costPrice;

    /** 市场价*/
    private BigDecimal marketPrice;

    /** 是否默认为SKU（0 否，1 是）*/
    private String isDefault;

    /** 所属的SPU*/
    private Long goodsId;

    /** 卖家ID*/
    private String sellerId;

    private String cartThumbnail;

    /** 分类名称*/
    private String category;

    /** 品牌名称*/
    private String brand;

    /** SKU部分描述*/
    private String spec;

    /** 商家名称*/
    private String seller;

    private static final long serialVersionUID = 1L;

}