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

import java.util.List;
import java.util.Map;

/**
 * 商品目录服务实现
 * @author Wwl
 *
 */

@Service
public class ItemCatalogServiceImpl implements ItemCatalogService {

	@Autowired
	private ItemCatalogMapper itemCatalogMapper;

	@Override
	public void add(ItemCatalog itemCatalog) {
		itemCatalogMapper.insert(itemCatalog);
	}

	@Override
	public void update(ItemCatalog itemCatalog) {
		itemCatalogMapper.updateByPrimaryKey(itemCatalog);
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
	}

	@Override
	public List<ItemCatalog> findByParentId(Long id) {
		ItemCatalogExample example = new ItemCatalogExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		return itemCatalogMapper.selectByExample(example);
	}

}
