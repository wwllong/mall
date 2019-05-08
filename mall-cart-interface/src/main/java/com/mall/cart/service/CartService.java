package com.mall.cart.service;

import com.mall.pojogroup.Cart;

import java.util.List;

/**
 * @author Jack Wen
 * @className CartService
 * @dsecription 购物车服务接口
 * @data 2019/5/6
 * @vserion 1.0
 */

public interface CartService {

    /**
     * 添加商品到购物车
     * @param itemId SKU_ID
     * @param num 商品数量
     * @param cartList 购物车列表
     * @return
     */
    public List<Cart> addGoods2CarList(Long itemId,Integer num,List<Cart> cartList);

    /**
     * 根据登陆用户名称 从Redis取出购物车列表
     * @param username 登陆用户名称
     * @return
     */
    public List<Cart> findCartListFromRedis(String username);

    /**
     * 保存购物车列表到Redis
     * @param username 登陆用户名称
     * @param cartList 购物车列表
     * @return
     */
    public void saveCartList2Redis(String username,List<Cart> cartList);

    /**
     * 合并购物车列表
     * @param cartList1 购物车列表
     * @param cartList2 购物车列表
     * @return
     */
    public List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);

}
