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
import org.springframework.data.redis.core.RedisTemplate;
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

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void add(Content content) {
		contentMapper.insert(content);
		//清空缓存
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
	}

	@Override
	public void update(Content content) {
		//清除原分类ID的缓存
		Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
		redisTemplate.boundHashOps("content").delete(categoryId);
		contentMapper.updateByPrimaryKey(content);
		//分类ID发生了修改，清除修改后的分类ID缓存
		if(!categoryId.equals(content.getCategoryId())){
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
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
			//清除缓存
			Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();
			redisTemplate.boundHashOps("content").delete(categoryId);
			contentMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		Content content = new Content();
		content.setStatus(status);
		for(Long id : ids){
			//清除缓存
			Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();
			redisTemplate.boundHashOps("content").delete(categoryId);
			content.setId(id);
			contentMapper.updateByPrimaryKeySelective(content);
		}
	}

	@Override
	public List<Content> findByCategoryId(Long categoryId) {
		//从redis拿数据
		List<Content> contentList = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);

		if(contentList == null){
			//从数据库中读取数据，根据广告分类ID查询列表，启用且排序
			ContentExample example = new ContentExample();
			ContentExample.Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			criteria.andStatusEqualTo("1");
			example.setOrderByClause("sort_order");
			contentList = contentMapper.selectByExample(example);
			//存入缓存
			redisTemplate.boundHashOps("content").put(categoryId,contentList);
		}
		return contentList;
	}

}
