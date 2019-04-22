package com.mall.shop.controller;

import com.mall.goods.service.GoodsService;
import com.mall.pojo.Goods;
import com.mall.pojogroup.GoodsGroup;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品SPU 控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 新增
     *
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsGroup goodsGroup) {
        try {
            //获取登陆名称
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsGroup.getGoods().setSellerId(sellerId);

            goodsService.add(goodsGroup);

            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods) {
        try {
            goodsService.update(goods);
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
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Goods> findAll() {
        return goodsService.findAll();
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
        return goodsService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param goods
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int size) {
        return goodsService.findPage(goods, page, size);
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
            goodsService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}