package com.mall.content.service;

import com.mall.pojo.ContentCategory;
import common.pojo.PageResult;

import java.util.List;

/**
 * 内容/广告类目服务接口
 * @author Wwl
 *
 */
public interface ContentCategoryService {

	/**
	 * 增加
	 * @param contentCategory
	 */
	public void add(ContentCategory contentCategory);

	/**
	 * 修改
	 * @param contentCategory
	 */
	public void update(ContentCategory contentCategory);

	/**
	 * 根据Id获取实体
	 * @param id
	 * */
	public ContentCategory findOne(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	public List<ContentCategory> findAll();

	/**
	 * 查询所有-分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 根据条件查询，分页
	 * @param contentCategory
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(ContentCategory contentCategory, int pageNum, int pageSize);

	/**
	 * 删除
	 * @return
	 */
	public void delete(Long[] ids);

	
}
