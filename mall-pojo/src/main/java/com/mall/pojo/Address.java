package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jack Wen.
 * @ClassName Address
 * @dsecription 收货地址表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 3834267982389198875L;

    private Long id;

    /** 用户ID(username)*/
    private String userId;

    /** 省*/
    private String provinceId;

    /** 市*/
    private String cityId;

    /** 县/区*/
    private String townId;

    /** 手机*/
    private String mobile;

    /** 详细地址*/
    private String address;

    /** 联系人*/
    private String contact;

    /** 是否默认 1默认 0否*/
    private String isDefault;

    /** 备注*/
    private String notes;

    /** 创建日期*/
    private Date createDate;

    /** 别名*/
    private String alias;

}