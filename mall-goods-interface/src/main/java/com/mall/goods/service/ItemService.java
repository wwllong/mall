package com.mall.goods.service;

import com.mall.pojo.Item;
import common.pojo.PageResult;

import java.util.List;

/**
 * 商品SKU服务接口
 * @author Wwl
 *
 */
public interface ItemService {

	/**
	 * 增加
	 * @param item
	 */
	public void add(Item item);

	/**
	 * 修改
	 * @param item
	 */
	public void update(Item item);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Item findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Item> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param item
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Item item, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

}
