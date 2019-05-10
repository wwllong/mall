package com.mall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.mall.cart.service.CartService;
import com.mall.pojogroup.Cart;
import com.mall.utils.CookieUtil;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Jack Wen
 * @className CartController
 * @dsecription 购物车服务控制层
 * @data 2019/5/6
 * @vserion 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 5000)
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 添加商品到购物车
     * @param itemId SKU_ID
     * @param num 商品数量
     * @return
     */
    @RequestMapping("/addGoods2CarList")
    @CrossOrigin(origins = "http://localhost:9105")
    public Result addGoods2CarList(Long itemId, Integer num) {
//        //CORS-跨域资源共享 跨域请求设置
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:9105");
//        //允许携带凭证，方法中操作了cookie,需要加上该响应头
//        response.setHeader("Access-Control-Allow-Credentials", "true");

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //1.从 Cookie/Redis 获取购物车列表
        List<Cart> cartList = findCartList();
        //2.添加商品
        cartList = cartService.addGoods2CarList(itemId,num,cartList);
        try {
            //3.判断用户有无登陆
            if("anonymousUser".equals(userName)){
                System.out.println("向Cookie中保存购物车数据.....");
                //4.如果没有，向Cookie中添加购物车列表,过期时间24小时
                CookieUtil.setCookie(request,response,
                        "cartList", JSON.toJSONString(cartList),
                        3600*24,"UTF-8");
                return Result.success("添加成功");
            }else{
                //5.如果有，向Redis中添加购物车列表
                System.out.println("向redis中保存购物车数据....."+userName);
                cartService.saveCartList2Redis(userName,cartList);
                return Result.success("添加成功");
            }
        } catch (Exception e){
            e.printStackTrace();
            return Result.error("添加失败");
        }
    }

    /**
     * 获取购物列表
     * @return
     */
    @RequestMapping("/findCartList")
    public List<Cart> findCartList(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        //1. 从Cookie中获取购物车列表
        String cartListStr = CookieUtil.getCookieValue(request,"cartList","UTF-8");
        //合法性检查
        if(cartListStr==null || cartListStr.equals("")){
            cartListStr = "[]";
        }
        //1.2 JSON字符串转换为对象
        List<Cart> cartList_cookie = JSON.parseArray(cartListStr, Cart.class);

        //2.判断用户有无登陆
        if("anonymousUser".equals(userName)){
            System.out.println("从Cookie中提取购物车数据.....");
            //3.如果没有，从Cookie中获取购物车列表
            return cartList_cookie;
        }else{
            System.out.println("从redis中提取购物车数据....."+userName);
            //4.如果有，从Redis中获取购物车列表
            List<Cart> cartList_redis = cartService.findCartListFromRedis(userName);
            //5.判断是否需要合并购物车
            if(cartList_cookie.size()>0){
                System.out.println("合并购物车.....");
                //5.1合并购物车
                List<Cart> cartList_merge = cartService.mergeCartList(cartList_redis, cartList_cookie);
                cartService.saveCartList2Redis(userName,cartList_merge);
                //5.2清除Cookie购物车
                CookieUtil.deleteCookie(request,response,"cartList");
                return cartList_merge;
            }
            return cartList_redis;
        }
    }

}
