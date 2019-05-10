package com.mall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.order.service.OrderService;
import com.mall.pojo.Order;
import com.mall.pojogroup.Cart;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 订单服务控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    /**
     * 新增
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,String> modelMap) {
        //获取当前登录人账号
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Cart> payCartList = JSON.parseArray(modelMap.get("payCartList"), Cart.class);
        Order order = JSON.parseObject(modelMap.get("order"),Order.class);
        order.setUserId(username);
        //订单来源  PC
        order.setSourceType("2");
        try {
            orderService.add(order,payCartList);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param order
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Order order) {
        try {
            orderService.update(order);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 查找一条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Order findOne(Long id) {
        return orderService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    /**
     * 列表查询-分页
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/listByPage")
    public PageResult findPage(int page, int size) {
        return orderService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param order
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Order order, int page, int size) {
        return orderService.findPage(order, page, size);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            orderService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}