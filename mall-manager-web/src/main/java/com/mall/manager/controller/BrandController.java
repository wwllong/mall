package com.mall.manager.controller;

import java.util.List;
import java.util.Map;

import com.mall.pojo.Brand;
import common.pojo.Result;
import common.pojo.PageResult;
import com.mall.goods.service.BrandService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    /**
     * 新增
     *
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand) {
        try {
            brandService.add(brand);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
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
    public Brand findOne(Long id) {
        return brandService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Brand> findAll() {
        return brandService.findAll();
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
        return brandService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand, int page, int size) {
        return brandService.findPage(brand, page, size);
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
            brandService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 下拉列表
     *
     * @return
     */
    @RequestMapping("/findOptionList")
    public List<Map> findOptionList() {
        return brandService.findOptionList();
    }
}