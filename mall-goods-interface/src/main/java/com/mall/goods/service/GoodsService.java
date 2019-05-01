package com.mall.goods.service;

import com.mall.pojo.Goods;
import com.mall.pojo.Item;
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
	 * @param goodsGroup
	 */
	public void update(GoodsGroup goodsGroup);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public GoodsGroup findOne(Long id);

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

	/**
	 * 批量上下架
	 * @param ids
	 * @param status
	 */
	public void updateMarketable(Long[] ids,String status);

	/**
	 * 批量修改状态
	 * @param ids
	 * @param status
	 */
	public void updateStatus(Long[] ids,String status);


	/**
	 * 根据商品Id和商品状态查询SKU列表
	 * @param goodsId 商品SPU id
	 * @param status 商品状态
	 * @return
	 */
	public List<Item> findItemListByGoodsIdAndStatus(Long[] goodsId,String status);
}
