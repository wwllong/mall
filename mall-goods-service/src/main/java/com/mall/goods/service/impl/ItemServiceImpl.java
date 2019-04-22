package com.mall.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.ItemService;
import com.mall.mapper.ItemMapper;
import com.mall.pojo.Item;
import com.mall.pojo.ItemExample;
import com.mall.pojo.ItemExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 商品SKU服务实现
 * @author Wwl
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public void add(Item item) {
		itemMapper.insert(item);
	}

	@Override
	public void update(Item item) {
		itemMapper.updateByPrimaryKey(item);
	}

	@Override
	public Item findOne(Long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Item> findAll() {
		return itemMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Item> page = (Page<Item>) itemMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Item item, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		ItemExample example = new ItemExample();
		Criteria criteria = example.createCriteria();
		if(item!=null){
			if(!StringUtils.isEmpty(item.getTitle())){
				criteria.andTitleLike("%"+item.getTitle()+"%");
			}
			if(!StringUtils.isEmpty(item.getSellPoint())){
				criteria.andSellPointLike("%"+item.getSellPoint()+"%");
			}
			if(!StringUtils.isEmpty(item.getBarcode())){
				criteria.andBarcodeLike("%"+item.getBarcode()+"%");
			}
			if(!StringUtils.isEmpty(item.getImage())){
				criteria.andImageLike("%"+item.getImage()+"%");
			}
			if(!StringUtils.isEmpty(item.getStatus())){
				criteria.andStatusLike("%"+item.getStatus()+"%");
			}
			if(!StringUtils.isEmpty(item.getItemSn())){
				criteria.andItemSnLike("%"+item.getItemSn()+"%");
			}
			if(!StringUtils.isEmpty(item.getIsDefault())){
				criteria.andIsDefaultLike("%"+item.getIsDefault()+"%");
			}
			if(!StringUtils.isEmpty(item.getSellerId())){
				criteria.andSellerIdLike("%"+item.getSellerId()+"%");
			}
			if(!StringUtils.isEmpty(item.getCartThumbnail())){
				criteria.andCartThumbnailLike("%"+item.getCartThumbnail()+"%");
			}
			if(!StringUtils.isEmpty(item.getCategory())){
				criteria.andCategoryLike("%"+item.getCategory()+"%");
			}
			if(!StringUtils.isEmpty(item.getBrand())){
				criteria.andBrandLike("%"+item.getBrand()+"%");
			}
			if(!StringUtils.isEmpty(item.getSpec())){
				criteria.andSpecLike("%"+item.getSpec()+"%");
			}
			if(!StringUtils.isEmpty(item.getSeller())){
				criteria.andSellerLike("%"+item.getSeller()+"%");
			}
		}
		Page<Item> page = (Page<Item>)itemMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			itemMapper.deleteByPrimaryKey(id);
		}
	}


}
