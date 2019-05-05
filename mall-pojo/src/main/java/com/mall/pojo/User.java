package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jack Wen.
 * @ClassName User
 * @dsecription 用户表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -243222180263125671L;

    private Long id;

    /** 用户名*/
    private String username;

    /** 密码*/
    private String password;

    /** 注册手机号*/
    private String phone;

    /** 注册邮箱*/
    private String email;

    /** 创建时间*/
    private Date created;

    /** 更新时间*/
    private Date updated;

    /** 会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat*/
    private String sourceType;

    /** 昵称*/
    private String nickName;

    /** 真实姓名*/
    private String name;

    /** 使用状态（Y正常 N非正常）*/
    private String status;

    /** 头像地址*/
    private String headPic;

    /** QQ号码*/
    private String qq;

    /** 账户余额*/
    private Long accountBalance;

    /** 手机是否验证 （0否  1是）*/
    private String isMobileCheck;

    /** 邮箱是否检测（0否  1是）*/
    private String isEmailCheck;

    /** 性别，1男，2女*/
    private String sex;

    /** 会员等级*/
    private Integer userLevel;

    /** 积分*/
    private Integer points;

    /** 经验值*/
    private Integer experienceValue;

    /** 生日*/
    private Date birthday;

    /** 最后登录时间*/
    private Date lastLoginTime;

}