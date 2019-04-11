package com.mall.sellergoods.service;

import java.util.List;

import com.mall.pojo.Brand;

import common.pojo.PageResult;

/**
 * 品牌接口
 * @author Wwl
 *
 */
public interface BrandService {

	/**
	 * 增加
	 * */
	public void add(Brand brand);

	/**
	 * 更新
	 * */
	public void update(Brand brand);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Brand findOne(Long id);
	
	public List<Brand> findAll();
	
	/**
	 * 分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);

	/**
	 * 分页
	 * @param brand 品牌
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
