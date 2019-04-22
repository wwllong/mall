package com.mall.goods.service;

import com.mall.pojo.GoodsDesc;
import common.pojo.PageResult;

import java.util.List;

/**
 * 商品SPU明细/扩展服务接口
 * @author Wwl
 *
 */
public interface GoodsDescService {

	/**
	 * 增加
	 * @param goodsDesc
	 */
	public void add(GoodsDesc goodsDesc);

	/**
	 * 修改
	 * @param goodsDesc
	 */
	public void update(GoodsDesc goodsDesc);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public GoodsDesc findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<GoodsDesc> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param goodsDesc
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(GoodsDesc goodsDesc, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

}
