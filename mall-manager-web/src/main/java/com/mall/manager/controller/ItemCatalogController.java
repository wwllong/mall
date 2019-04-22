package com.mall.manager.controller;

import com.mall.goods.service.ItemCatalogService;
import com.mall.pojo.ItemCatalog;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/itemCatalog")
public class ItemCatalogController {

    @Reference
    private ItemCatalogService itemCatalogService;

    /**
     * 新增
     *
     * @param itemCatalog
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody ItemCatalog itemCatalog) {
        try {
            itemCatalogService.add(itemCatalog);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param itemCatalog
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody ItemCatalog itemCatalog) {
        try {
            itemCatalogService.update(itemCatalog);
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
    public ItemCatalog findOne(Long id) {
        return itemCatalogService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<ItemCatalog> findAll() {
        return itemCatalogService.findAll();
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
        return itemCatalogService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param itemCatalog
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody ItemCatalog itemCatalog, int page, int size) {
        return itemCatalogService.findPage(itemCatalog, page, size);
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
            itemCatalogService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 根据父ID查询列表
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCatalog> findByParentId(Long parentId) {
        return itemCatalogService.findByParentId(parentId);
    }

}