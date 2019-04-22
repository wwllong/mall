package com.mall.shop.controller;

import com.mall.goods.service.ItemService;
import com.mall.pojo.Item;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品SKU 控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Reference
    private ItemService itemService;

    /**
     * 新增
     *
     * @param item
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Item item) {
        try {
            itemService.add(item);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param item
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Item item) {
        try {
            itemService.update(item);
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
    public Item findOne(Long id) {
        return itemService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Item> findAll() {
        return itemService.findAll();
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
        return itemService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param item
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Item item, int page, int size) {
        return itemService.findPage(item, page, size);
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
            itemService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}