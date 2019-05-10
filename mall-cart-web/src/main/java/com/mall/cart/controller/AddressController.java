package com.mall.cart.controller;

import com.mall.cart.service.CartService;
import com.mall.pojo.Item;
import com.mall.pojo.OrderItem;
import com.mall.pojogroup.Cart;
import com.mall.user.service.AddressService;
import com.mall.pojo.Address;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference(timeout = 5000)
    private AddressService addressService;

    @Reference
    private CartService cartService;

    /**
     * 新增
     *
     * @param address
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Address address) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userId==null ||userId.equals("")){
            return Result.error("非法操作");
        }
        try {
            address.setUserId(userId);
            addressService.add(address);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param address
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Address address) {
        try {
            addressService.update(address);
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
    public Address findOne(Long id) {
        return addressService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Address> findAll() {
        return addressService.findAll();
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
        return addressService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param address
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Address address, int page, int size) {
        return addressService.findPage(address, page, size);
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
            addressService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 根据用户ID查询用户送货地址
     *
     * @return
     */
    @RequestMapping("/findListByLoginUser")
    public List<Address> findListByLoginUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByUserId(userId);
    }

    /**
     * 获取订单购物车
     * @return
     */
    @RequestMapping("getOrderCartList")
    public List<Cart> getOrderCartList(@RequestBody List<OrderItem> itemList){
        List<Cart> payCartList = new ArrayList<>();
        if(itemList!=null){
            for(OrderItem orderItem : itemList){
                payCartList= cartService.addGoods2CarList(orderItem.getItemId(),orderItem.getNum(),payCartList);
            }
        }
        return payCartList;
    }


}