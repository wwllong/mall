package com.mall.user.service;

import com.mall.pojo.User;
import common.pojo.PageResult;

import java.util.List;

/**
 * 用户服务接口
 * @author Wwl
 *
 */
public interface UserService {

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user);

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public User findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<User> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param user
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(User user, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

	/**
	 * 根据用户名称查询用户
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 生成短信验证码
	 * @param phone
	 */
	public void createSmsCode(String phone);

	/**
	 * 校验短信验证码
	 * @param phone
	 */
	public boolean checkSmsCode(String phone,String code);

}
