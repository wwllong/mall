package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jack Wen.
 * @ClassName OrderItem
 * @dsecription 订单项/明细表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1141853378007571249L;

    private Long id;

    /** SKU_ID*/
    private Long itemId;

    /** SPU_ID*/
    private Long goodsId;

    /** 订单ID*/
    private Long orderId;

    /** 商品标题*/
    private String title;

    /** 商品单价*/
    private BigDecimal price;

    /** 商品购买数量*/
    private Integer num;

    /** 商品总金额*/
    private BigDecimal totalFee;

    /** 商品图片地址*/
    private String picPath;

    /** 卖家ID*/
    private String sellerId;

}