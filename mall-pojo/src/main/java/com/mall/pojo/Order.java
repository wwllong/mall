package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jack Wen.
 * @ClassName Order
 * @dsecription 订单表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Order implements Serializable {

    private Long orderId;

    /** 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分*/
    private BigDecimal payment;

    /** 支付类型，1、微信支付，2、货到付款，3、支付宝支付*/
    private String paymentType;

    /** 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分*/
    private String postFee;

    /** 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价*/
    private String status;

    /** 订单创建时间*/
    private Date createTime;

    /** 订单更新时间*/
    private Date updateTime;

    /** 付款时间*/
    private Date paymentTime;

    /** 发货时间*/
    private Date consignTime;

    /** 交易完成时间*/
    private Date endTime;

    /** 交易关闭时间*/
    private Date closeTime;

    /** 物流名称*/
    private String shippingName;

    /** 物流单号*/
    private String shippingCode;

    /** 用户id（username*/
    private String userId;

    /** 买家留言*/
    private String buyerMessage;

    /** 买家昵称*/
    private String buyerNick;

    /** 买家是否已经评价（0 否 1 是）*/
    private String buyerRate;

    /** 收货人地区名称(省，市，县)街道*/
    private String receiverAreaName;

    /** 收货人手机*/
    private String receiverMobile;

    /** 收货人邮编*/
    private String receiverZipCode;

    /** 收货人*/
    private String receiver;

    /** 过期时间，定期清理*/
    private Date expire;

    /** 发票类型(普通发票，电子发票，增值税发票)*/
    private String invoiceType;

    /** 订单来源：1：安卓端， 2：ios端，3：pc端，4：手机端，5：微信端，6：手机qq端*/
    private String sourceType;

    /** 商家ID*/
    private String sellerId;

}