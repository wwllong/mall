package com.mall.user.service;

import com.mall.pojo.Address;
import common.pojo.PageResult;

import java.util.List;

/**
 * 收货地址服务接口
 * @author Wwl
 *
 */
public interface AddressService {

	/**
	 * 增加
	 * @param address
	 */
	public void add(Address address);

	/**
	 * 修改
	 * @param address
	 */
	public void update(Address address);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Address findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Address> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param address
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Address address, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

	/**
	 * 根据用户ID查询用户送货地址
	 * @param userId 用户ID
	 * @return 用户送货地址
	 */
	public List<Address> findListByUserId(String userId);

}
