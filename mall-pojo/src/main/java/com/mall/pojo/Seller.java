package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jack Wen.
 * @ClassName Seller
 * @dsecription 卖家/商家用户
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Seller implements Serializable {

    /** 用户ID（登录名）*/
    private String sellerId;

    /** 公司名 */
    private String name;

    /** 店铺名称 */
    private String nickName;

    /** 密码 */
    private String password;

    /** 邮件 */
    private String email;

    /** 公司手机 */
    private String mobile;

    /** 公司电话 */
    private String telephone;

    /** 状态（0 未审核，1 已审核，2 审核未通过，3 关闭商家） */
    private String status;

    /** 详细地址 */
    private String addressDetail;

    /** 联系人姓名 */
    private String linkmanName;

    /** 联系人QQ */
    private String linkmanQq;

    /** 联系人电话 */
    private String linkmanMobile;

    /** 联系人邮箱 */
    private String linkmanEmail;

    /** 营业执照号 */
    private String licenseNumber;

    /** 税务登记证号 */
    private String taxNumber;

    /** 组织机构代码 */
    private String orgNumber;

    /** 公司地址 */
    private Long address;

    /** 公司LOGO图 */
    private String logoPic;

    /** 简介 */
    private String brief;

    /** 创建日期 */
    private Date createTime;

    /** 法定代表人 */
    private String legalPerson;

    /** 法定代表人身份证 */
    private String legalPersonCardId;

    /** 开户行账号名称 */
    private String bankUser;

    /** 开户行 */
    private String bankName;

}