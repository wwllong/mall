package com.mall.manager.controller;

import org.apache.dubbo.config.annotation.Reference;
import com.mall.pojo.Specification;
import com.mall.pojogroup.SpecificationGroup;
import com.mall.goods.service.SpecificationService;
import common.pojo.PageResult;
import common.pojo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 规格控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    /**
     * 新增
     *
     * @param specificationGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationGroup specificationGroup) {
        try {
            specificationService.add(specificationGroup);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param specificationGroup
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationGroup specificationGroup) {
        try {
            specificationService.update(specificationGroup);
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
    public SpecificationGroup findOne(Long id) {
        return specificationService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Specification> findAll() {
        return specificationService.findAll();
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
        return specificationService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param specification
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Specification specification, int page, int size) {
        return specificationService.findPage(specification, page, size);
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
            specificationService.delete(ids);
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
        return specificationService.findOptionList();
    }
}