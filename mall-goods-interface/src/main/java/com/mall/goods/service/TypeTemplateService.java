package com.mall.goods.service;

import com.mall.pojo.TypeTemplate;
import common.pojo.PageResult;

import java.util.List;

/**
 * 商品类型模板服务接口
 * @author Wwl
 *
 */
public interface TypeTemplateService {

	/**
	 * 增加
	 * @param typeTemplate
	 */
	public void add(TypeTemplate typeTemplate);

	/**
	 * 修改
	 * @param typeTemplate
	 */
	public void update(TypeTemplate typeTemplate);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public TypeTemplate findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<TypeTemplate> findAll();
	
	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param typeTemplate
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

}
