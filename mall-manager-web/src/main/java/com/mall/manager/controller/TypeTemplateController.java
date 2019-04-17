package com.mall.manager.controller;

import com.mall.goods.service.TypeTemplateService;
import com.mall.pojo.TypeTemplate;
import common.pojo.Message;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
			return  new Result(true, Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return  new Result(false,Message.ADMIN_FAIL);
		}

	}

	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("update")
	public Result update(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
			return new Result(true,Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}

	/**
	 * 查找一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping("findOne")
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
	@RequestMapping("delete")
	public Result delete(Long[] ids){
		try {
			typeTemplateService.delete(ids);
			return new Result(true,Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}
}