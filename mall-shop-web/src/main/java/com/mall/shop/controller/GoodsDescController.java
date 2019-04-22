package com.mall.shop.controller;

import com.mall.goods.service.GoodsDescService;
import com.mall.pojo.GoodsDesc;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品SPU 明细/扩展 控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

    @Reference
    private GoodsDescService goodsDescService;

    /**
     * 新增
     *
     * @param goodsDesc
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsDesc goodsDesc) {
        try {
            goodsDescService.add(goodsDesc);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param goodsDesc
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsDesc goodsDesc) {
        try {
            goodsDescService.update(goodsDesc);
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
    public GoodsDesc findOne(Long id) {
        return goodsDescService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<GoodsDesc> findAll() {
        return goodsDescService.findAll();
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
        return goodsDescService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param goodsDesc
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody GoodsDesc goodsDesc, int page, int size) {
        return goodsDescService.findPage(goodsDesc, page, size);
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
            goodsDescService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}