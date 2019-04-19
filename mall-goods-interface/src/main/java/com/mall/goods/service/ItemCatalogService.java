package com.mall.goods.service;

import com.mall.pojo.ItemCatalog;
import common.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 商品目录/分类服务接口
 * @author Wwl
 *
 */
public interface ItemCatalogService {

	/**
	 * 增加
	 * @param catalog
	 */
	public void add(ItemCatalog catalog);

	/**
	 * 修改
	 * @param catalog
	 */
	public void update(ItemCatalog catalog);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public ItemCatalog findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<ItemCatalog> findAll();
	
	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param catalog
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(ItemCatalog catalog, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

	/**
	 * 根据父ID查询列表
	 * @param id
	 * @return
	 */
	public List<ItemCatalog> findByParentId(Long id);

}
