package com.mall.manager.controller;

import org.apache.dubbo.config.annotation.Reference;
import com.mall.pojo.SpecificationOption;
import com.mall.goods.service.SpecificationOptionService;
import common.pojo.Message;
import common.pojo.PageResult;
import common.pojo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {
	
	@Reference
	private SpecificationOptionService specificationOptionService;

	/**
	 * 新增
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody SpecificationOption specificationOption){
		try {
			specificationOptionService.add(specificationOption);
			return  new Result(true, Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return  new Result(false,Message.ADMIN_FAIL);
		}

	}

	/**
	 * 修改
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("update")
	public Result update(@RequestBody SpecificationOption specificationOption){
		try {
			specificationOptionService.update(specificationOption);
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
	public SpecificationOption findOne(Long id){
		return specificationOptionService.findOne(id);
	}

	/**
	 * 列表查询
	 * @return
	 */
	@RequestMapping("/list")
	public List<SpecificationOption> findAll(){
		return specificationOptionService.findAll();
	}

	/**
	 * 列表查询-分页
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/listByPage")
	public PageResult findPage(int page, int size){
		return specificationOptionService.findPage(page, size);
	}

	/**
	 * 条件查询-分页
	 * @param specificationOption
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody SpecificationOption specificationOption, int page, int size){
		return specificationOptionService.findPage(specificationOption, page, size);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	public Result delete(Long[] ids){
		try {
			specificationOptionService.delete(ids);
			return new Result(true,Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}
}