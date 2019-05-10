package com.mall.order.service;

import com.mall.pojo.Order;
import com.mall.pojogroup.Cart;
import common.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 * @author Wwl
 *
 */
public interface OrderService {

	/**
	 * 增加
	 * @param order
	 * @param payCartList 购物车Json对象
	 */
	public void add(Order order,List<Cart> payCartList);

	/**
	 * 修改
	 * @param order
	 */
	public void update(Order order);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Order findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Order> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param order
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Order order, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);


}
