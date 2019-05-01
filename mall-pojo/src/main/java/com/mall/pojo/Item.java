package com.mall.pojo;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author Jack Wen.
 * @ClassName Item
 * @dsecription 商品SKU表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Item implements Serializable {

    private static final long serialVersionUID = 3147399576278674226L;

    @Field
    private Long id;

    /** 商品标题*/
    @Field("item_title")
    private String title;

    /** 商品卖点*/
    private String sellPoint;

    /** 商品价格，元*/
    @Field("item_price")
    private BigDecimal price;

    /** 存货数量*/
    private Integer stockCount;

    /** 库存数量*/
    private Integer num;

    /** 商品条形码*/
    private String barcode;

    /** 商品图片，第一张*/
    @Field("item_image")
    private String image;

    /** 商品所属类目，叶子目录*/
    private Long categoryId;

    /** 商品状态，(0 未处理 1 正常，2 下架，3 关闭)*/
    private String status;

    /** 创建时间*/
    private Date createTime;

    /** 更新时间*/
    @Field("item_updatetime")
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
    @Field("item_goodsid")
    private Long goodsId;

    /** 卖家ID*/
    private String sellerId;

    private String cartThumbnail;

    /** 分类名称*/
    @Field("item_category")
    private String category;

    /** 品牌名称*/
    @Field("item_brand")
    private String brand;

    /** SKU部分描述*/
    private String spec;

    /** 商家名称*/
    @Field("item_seller")
    private String seller;

    /** 规格动态域*/
    @Dynamic
    @Field("item_spec_*")
    private Map<String,String> specMap;


}