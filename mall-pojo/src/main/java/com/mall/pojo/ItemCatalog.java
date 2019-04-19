package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName ItemCatalog
 * @dsecription 商品类目/分类表
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class ItemCatalog implements Serializable {

    private Long id;

    /** 父分类ID*/
    private Long parentId;

    /** 分类名称*/
    private String name;

    /** 类型模板id*/
    private Long typeId;

    private static final long serialVersionUID = 1L;


}