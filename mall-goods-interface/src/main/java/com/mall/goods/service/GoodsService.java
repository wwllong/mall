package com.mall.goods.service;

import com.mall.pojo.Goods;
import com.mall.pojogroup.GoodsGroup;
import common.pojo.PageResult;

import java.util.List;

/**
 * 商品SPU服务接口
 * @author Wwl
 *
 */
public interface GoodsService {

	/**
	 * 增加
	 * @param goodsGroup
	 */
	public void add(GoodsGroup goodsGroup);

	/**
	 * 修改
	 * @param goods
	 */
	public void update(Goods goods);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Goods findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Goods> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param goods
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Goods goods, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

}
