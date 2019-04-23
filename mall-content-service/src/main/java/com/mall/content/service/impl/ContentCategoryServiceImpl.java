package com.mall.content.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.content.service.ContentCategoryService;
import com.mall.mapper.ContentCategoryMapper;
import com.mall.pojo.ContentCategory;
import com.mall.pojo.ContentCategoryExample;
import com.mall.pojo.ContentCategoryExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 内容/广告分类服务实现
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	@Override
	public void add(ContentCategory contentCategory) {
		contentCategoryMapper.insert(contentCategory);
	}

	@Override
	public void update(ContentCategory contentCategory) {
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
	}

	@Override
	public ContentCategory findOne(Long id) {
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ContentCategory> findAll() {
		return contentCategoryMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<ContentCategory> page = (Page<ContentCategory>) contentCategoryMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(ContentCategory contentCategory, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		ContentCategoryExample example = new ContentCategoryExample();
		Criteria criteria = example.createCriteria();
		if(contentCategory!=null){
			if(!StringUtils.isEmpty(contentCategory.getName())){
				criteria.andNameLike("%"+contentCategory.getName()+"%");
			}
		}
		Page<ContentCategory> page = (Page<ContentCategory>)contentCategoryMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			contentCategoryMapper.deleteByPrimaryKey(id);
		}
	}

}
