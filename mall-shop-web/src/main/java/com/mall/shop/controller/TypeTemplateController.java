package com.mall.shop.controller;

import com.mall.goods.service.TypeTemplateService;
import com.mall.pojo.TypeTemplate;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品类型模板控制层
 * @author Wwl
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
	
	@Reference
	private TypeTemplateService typeTemplateService;

	/**
	 * 新增
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.add(typeTemplate);
			return  Result.ADMIN_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return  Result.ADMIN_ERROR;
		}

	}

	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
			return  Result.ADMIN_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return  Result.ADMIN_ERROR;
		}
	}

	/**
	 * 查找一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TypeTemplate findOne(Long id){
		return typeTemplateService.findOne(id);
	}

	/**
	 * 列表查询
	 * @return
	 */
	@RequestMapping("/list")
	public List<TypeTemplate> findAll(){
		return typeTemplateService.findAll();
	}

	/**
	 * 列表查询-分页
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/listByPage")
	public PageResult findPage(int page, int size){
		return typeTemplateService.findPage(page, size);
	}

	/**
	 * 条件查询-分页
	 * @param typeTemplate
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TypeTemplate typeTemplate, int page, int size){
		return typeTemplateService.findPage(typeTemplate, page, size);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids){
		try {
			typeTemplateService.delete(ids);
			return Result.ADMIN_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ADMIN_ERROR;
		}
	}

	/**
	 * 下拉列表
	 * @return
	 */
	@RequestMapping("/findOptionList")
	public List<Map> findOptionList(){
		return typeTemplateService.findOptionList();
	}

	/**
	 * 返回规格列表以及各个规格的选项
	 * @param id 模板ID
	 * @return
	 */
	@RequestMapping("/findSpecListWithOptions")
	public List<Map> findSpecListWithOptions(Long id){
		return typeTemplateService.findSpecListWithOptions(id);
	}
}