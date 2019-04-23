package com.mall.content.service;

import com.mall.pojo.Content;
import common.pojo.PageResult;

import java.util.List;

/**
 * 内容/广告服务接口
 * @author Wwl
 *
 */
public interface ContentService {

	/**
	 * 增加
	 * @param content
	 */
	public void add(Content content);

	/**
	 * 修改
	 * @param content
	 */
	public void update(Content content);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public Content findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Content> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param content
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Content content, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);


}
