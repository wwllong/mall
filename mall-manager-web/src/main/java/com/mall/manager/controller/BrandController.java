package com.mall.manager.controller;

import java.util.List;

import common.pojo.Message;
import common.pojo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mall.pojo.Brand;
import com.mall.sellergoods.service.BrandService;

import common.pojo.PageResult;

@RestController
@RequestMapping("/brand")
public class BrandController {
	
	@Reference
	private BrandService brandService;

	/**
	 * 新增
	 * @param brand
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Brand brand){
		try {
			brandService.add(brand);
			System.out.println(brand.getName());
			return  new Result(true, Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return  new Result(false,Message.ADMIN_FAIL);
		}

	}

	/**
	 * 修改
	 * @param brand
	 * @return
	 */
	@RequestMapping("update")
	public Result update(@RequestBody Brand brand){
		try {
			brandService.update(brand);
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
	public Brand findOne(Long id){
		return brandService.findOne(id);
	}

	/**
	 * 列表查询
	 * @return
	 */
	@RequestMapping("/list")
	public List<Brand> findAll(){
		return brandService.findAll();
	}

	/**
	 * 列表分页查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/listByPage")
	public PageResult findPage(int page, int size){
		return brandService.findPage(page, size);
	}

	/**
	 * 列表分页-条件查询
	 * @param brand
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody Brand brand, int page, int size){
		return brandService.findPage(brand, page, size);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	public Result delete(Long[] ids){
		try {
			ids = null;
//			brandService.delete(ids);
			return new Result(true,Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}
}