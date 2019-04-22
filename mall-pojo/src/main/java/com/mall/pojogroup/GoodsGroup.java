package com.mall.pojogroup;

import com.mall.pojo.Goods;
import com.mall.pojo.GoodsDesc;
import com.mall.pojo.Item;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jack Wen
 * @className GoodsGroup
 * @dsecription 商品entity组合
 * @data 2019/4/20 0020
 * @vserion 1.0
 */
@Data
public class GoodsGroup implements Serializable {

    private static final long serialVersionUID = -2614732611777906006L;

    /** 商品SPU*/
    private Goods goods;

    /** 商品SPU 扩展信息*/
    private GoodsDesc goodsDesc;

    /** 商品SKU列表*/
    private List<Item> itemList;

}
