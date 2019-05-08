package com.mall.pojogroup;

import com.mall.pojo.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jack Wen
 * @className Cart
 * @dsecription 购物车,每个商家购物车
 * @data 2019/5/6
 * @vserion 1.0
 */

@Data
public class Cart implements Serializable {

    private static final long serialVersionUID = -6430309076822843276L;

    /** 商家ID*/
    private String sellerId;

    /** 商家名称*/
    private String sellerName;

    /** 购物车项/明细列表*/
    private List<OrderItem> orderItemList;

}
