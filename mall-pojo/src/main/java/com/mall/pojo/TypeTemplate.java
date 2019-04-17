package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName TypeTemplate
 * @dsecription 商品类型模板类
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class TypeTemplate implements Serializable {
    private Long id;

    private String name;

    private String specIds;

    private String brandIds;

    private String customAttributeItems;

    private static final long serialVersionUID = 1L;

}