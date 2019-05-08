package com.mall.cart.service.impl;

import com.mall.cart.service.CartService;
import com.mall.mapper.ItemMapper;
import com.mall.pojo.Item;
import com.mall.pojo.OrderItem;
import com.mall.pojogroup.Cart;
import org.apache.dubbo.config.annotation.Service;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Wen
 * @className CartServiceImpl
 * @dsecription 购物车服务实现
 * @data 2019/5/6
 * @vserion 1.0
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加商品到购物车
     *
     * @param itemId   SKU_ID
     * @param num      商品数量
     * @param cartList 购物车列表
     * @return
     */
    @Override
    public List<Cart> addGoods2CarList(Long itemId, Integer num, List<Cart> cartList) {
        //1.根据itemID查询SKU商品信息
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if(item==null){
            throw new RuntimeException(itemId+"商品不存在!");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException(itemId+"商品状态无效!");
        }
        //2.获取商家ID和名称
        String sellerId = item.getSellerId();
        String sellerName = item.getSeller();
        //3.根据商家ID判断购物车列表中是否存在该商家的购物车
        Cart cart = searchCartBySellerId(cartList,sellerId);
        //4.如果存在该商家的购物车
        if(cart!=null){
            //查询该购物车的列表是否存在该商品
            OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(),itemId);
            if(orderItem==null){
                //4.1 如果没有，新增购物车明细
                orderItem = createOrderItem(item,num);
                cart.getOrderItemList().add(orderItem);
            }else {
                //4.2 如果有，在原购物车明细上增加数量，修改金额
                orderItem.setNum(orderItem.getNum()+num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));
                    //数量操作合法性检查，非法移除明细
                if(orderItem.getNum()<=0){
                    cart.getOrderItemList().remove(orderItem);
                }
                    //购物车合法性检查，明细为0，移除购物车
                if(cart.getOrderItemList().size()==0){
                    cartList.remove(cart);
                }
            }

        }else{
        //5.如果不存在该商家的购物车
            //新建购物车对象
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(sellerName);
            OrderItem orderItem = createOrderItem(item, num);
            ArrayList<OrderItem> orderItemList = new ArrayList<>();
            orderItemList.add(orderItem);
            cart.setOrderItemList(orderItemList);
            //5.1将新建购物车对象添加到购物车列表中
            cartList.add(cart);
        }
        return cartList;
    }

    /**
     * 根据登陆用户名称 从Redis取出购物车列表
     *
     * @param username 登陆用户名称
     * @return
     */
    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>)redisTemplate.boundHashOps("cartList").get(username);
        if(cartList==null){
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    /**
     * 保存购物车列表到Redis
     *
     * @param username 登陆用户名称
     * @param cartList 购物车列表
     * @return
     */
    @Override
    public void saveCartList2Redis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username,cartList);
    }

    /**
     * 合并购物车列表
     *
     * @param cartList1 购物车列表
     * @param cartList2 购物车列表
     * @return
     */
    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        for(Cart cart : cartList2){
            for(OrderItem orderItem : cart.getOrderItemList()){
                cartList1 = addGoods2CarList(orderItem.getItemId(),orderItem.getNum(),cartList1);
            }
        }
        return cartList1;
    }

    /**
     * 创建购物车明细/订单明细
     * @param item SKU
     * @param num 数量
     * @return
     */
    private OrderItem createOrderItem(Item item, Integer num) {
        if (num<=0){
            throw new RuntimeException("非法的商品数量");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(item.getId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setNum(num);
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        orderItem.setPicPath(item.getImage());
        orderItem.setSellerId(item.getSellerId());
        //orderItem.setOrderId();
        return orderItem;
    }

    /**
     * 根据SKU_ID查询该购物车的明细
     * @param orderItemList 购物车明细列表
     * @param itemId SKU_ID
     * @return
     */
    private OrderItem searchOrderItemByItemId(List<OrderItem> orderItemList, Long itemId) {
        for(OrderItem orderItem : orderItemList){
            if(orderItem.getItemId().longValue()==itemId.longValue()){
                return orderItem;
            }
        }
        return null;
    }

    /**
     * 根据卖家ID查询购物车对象
     * @param cartList 购物车列表
     * @param sellerId 卖家ID
     * @return
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for(Cart cart : cartList){
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

}
