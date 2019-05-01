package com.mall.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.ItemCatalogService;
import com.mall.mapper.ItemCatalogMapper;
import com.mall.pojo.ItemCatalog;
import com.mall.pojo.ItemCatalogExample;
import com.mall.pojo.ItemCatalogExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 商品目录服务实现
 * @author Wwl
 *
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemCatalogServiceImpl implements ItemCatalogService {

	@Autowired
	private ItemCatalogMapper itemCatalogMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void add(ItemCatalog itemCatalog) {
		itemCatalogMapper.insert(itemCatalog);
		saveToRedis();
	}

	@Override
	public void update(ItemCatalog itemCatalog) {
		itemCatalogMapper.updateByPrimaryKey(itemCatalog);
		saveToRedis();
	}

	@Override
	public ItemCatalog findOne(Long id) {
		return itemCatalogMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ItemCatalog> findAll() {
		return itemCatalogMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<ItemCatalog> page = (Page<ItemCatalog>) itemCatalogMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(ItemCatalog itemCatalog, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		ItemCatalogExample example = new ItemCatalogExample();
		Criteria criteria = example.createCriteria();
		if(itemCatalog!=null){
			if(!StringUtils.isEmpty(itemCatalog.getName())){
				criteria.andNameLike("%"+itemCatalog.getName()+"%");
			}
		}
		Page<ItemCatalog> page = (Page<ItemCatalog>)itemCatalogMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			itemCatalogMapper.deleteByPrimaryKey(id);
		}
		saveToRedis();
	}

	@Override
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	public List<ItemCatalog> findByParentId(Long id) {
		ItemCatalogExample example = new ItemCatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		//缓存模板的品牌和规格列表
		if(redisTemplate.boundHashOps("itemCatalog").size()==0){
			saveToRedis();
		}
		return itemCatalogMapper.selectByExample(example);
	}

	/**
	 * 缓存模板的品牌和规格列表
	 */
	private void saveToRedis(){
		//缓存分类数据 itemCatalog：[{商品分类名称:模板ID}]
		for (ItemCatalog itemCatalog : findAll()){
			redisTemplate.boundHashOps("itemCatalog").put(itemCatalog.getName(),itemCatalog.getTypeId());
		}
	}


}
