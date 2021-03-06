package com.mall.goods.service;

import java.util.List;
import java.util.Map;

import com.mall.pojo.Specification;

import com.mall.pojogroup.SpecificationGroup;
import common.pojo.PageResult;

/**
 * 规格服务接口
 * @author Wwl
 *
 */
public interface SpecificationService {

	/**
	 * 增加
	 * @param specificationGroup
	 */
	public void add(SpecificationGroup specificationGroup);

	/**
	 * 修改
	 * @param specificationGroup
	 */
	public void update(SpecificationGroup specificationGroup);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public SpecificationGroup findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Specification> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param specification
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Specification specification,int pageNum,int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

	/**
	 * 下拉列表
	 * @return
	 */
	public List<Map> findOptionList();
}
