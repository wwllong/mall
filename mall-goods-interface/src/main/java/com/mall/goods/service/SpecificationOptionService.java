package com.mall.goods.service;

import com.mall.pojo.SpecificationOption;
import common.pojo.PageResult;

import java.util.List;

/**
 * 规格选项明细服务接口
 * @author Wwl
 *
 */
public interface SpecificationOptionService {

	/**
	 * 增加
	 * @param specificationOption
	 */
	public void add(SpecificationOption specificationOption);

	/**
	 * 修改
	 * @param specificationOption
	 */
	public void update(SpecificationOption specificationOption);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public SpecificationOption findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<SpecificationOption> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param specificationOption
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(SpecificationOption specificationOption, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

}
