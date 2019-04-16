package com.mall.goods.service;

import java.util.List;

import com.mall.pojo.Brand;

import common.pojo.PageResult;

/**
 * 品牌服务接口
 * @author Wwl
 *
 */
public interface BrandService {

	/**
	 * 增加
	 * @param brand
	 */
	public void add(Brand brand);

	/**
	 * 修改
	 * @param brand
	 */
	public void update(Brand brand);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Brand findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Brand> findAll();
	
	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param brand
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Brand brand,int pageNum,int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

}
