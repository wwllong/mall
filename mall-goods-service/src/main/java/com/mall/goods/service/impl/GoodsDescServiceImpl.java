package com.mall.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.GoodsDescService;
import com.mall.mapper.GoodsDescMapper;
import com.mall.pojo.GoodsDesc;
import com.mall.pojo.GoodsDescExample;
import com.mall.pojo.GoodsDescExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品SPU明细/扩展服务实现
 * @author Wwl
 *
 */
@Service
public class GoodsDescServiceImpl implements GoodsDescService {

	@Autowired
	private GoodsDescMapper goodsDescMapper;

	@Override
	public void add(GoodsDesc goodsDesc) {
		goodsDescMapper.insert(goodsDesc);
	}

	@Override
	public void update(GoodsDesc goodsDesc) {
		goodsDescMapper.updateByPrimaryKey(goodsDesc);
	}

	@Override
	public GoodsDesc findOne(Long id) {
		return goodsDescMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<GoodsDesc> findAll() {
		return goodsDescMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<GoodsDesc> page = (Page<GoodsDesc>) goodsDescMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(GoodsDesc goodsDesc, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		GoodsDescExample example = new GoodsDescExample();
		Criteria criteria = example.createCriteria();
		if(goodsDesc!=null){
			if(!StringUtils.isEmpty(goodsDesc.getIntroduction())){
				criteria.andIntroductionLike("%"+goodsDesc.getIntroduction()+"%");
			}
			if(!StringUtils.isEmpty(goodsDesc.getSpecificationItems())){
				criteria.andSpecificationItemsLike("%"+goodsDesc.getSpecificationItems()+"%");
			}
			if(!StringUtils.isEmpty(goodsDesc.getCustomAttributeItems())){
				criteria.andCustomAttributeItemsLike("%"+goodsDesc.getCustomAttributeItems()+"%");
			}
			if(!StringUtils.isEmpty(goodsDesc.getItemImages())){
				criteria.andItemImagesLike("%"+goodsDesc.getItemImages()+"%");
			}
			if(!StringUtils.isEmpty(goodsDesc.getPackageList())){
				criteria.andPackageListLike("%"+goodsDesc.getPackageList()+"%");
			}
			if(!StringUtils.isEmpty(goodsDesc.getSaleService())){
				criteria.andSaleServiceLike("%"+goodsDesc.getSaleService()+"%");
			}
		}
		Page<GoodsDesc> page = (Page<GoodsDesc>)goodsDescMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			goodsDescMapper.deleteByPrimaryKey(id);
		}
	}

}
