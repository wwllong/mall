package com.mall.goods.service;

import com.mall.pojo.Seller;
import common.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 商家/卖家服务接口
 * @author Wwl
 *
 */
public interface SellerService {

	/**
	 * 增加
	 * @param seller
	 */
	public void add(Seller seller);

	/**
	 * 修改
	 * @param seller
	 */
	public void update(Seller seller);

	/**
	 * 根据Id获取实体
	 * @param sellerId
	 * */
	public Seller findOne(String sellerId);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Seller> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param seller
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Seller seller, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(String[] ids);

}
