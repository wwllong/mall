package com.mall.content.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.content.service.ContentService;
import com.mall.mapper.ContentMapper;
import com.mall.pojo.Content;
import com.mall.pojo.ContentExample;
import com.mall.pojo.ContentExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 内容/广告服务实现
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;

	@Override
	public void add(Content content) {
		contentMapper.insert(content);
	}

	@Override
	public void update(Content content) {
		contentMapper.updateByPrimaryKey(content);
	}

	@Override
	public Content findOne(Long id) {
		return contentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Content> findAll() {
		return contentMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Content> page = (Page<Content>) contentMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Content content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		if(content!=null){
			if(!StringUtils.isEmpty(content.getTitle())){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(!StringUtils.isEmpty(content.getStatus())){
				criteria.andStatusEqualTo(content.getStatus());
			}
		}
		Page<Content> page = (Page<Content>)contentMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			contentMapper.deleteByPrimaryKey(id);
		}
	}

}
