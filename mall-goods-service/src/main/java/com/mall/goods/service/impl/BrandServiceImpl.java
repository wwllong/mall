package com.mall.goods.service.impl;

import java.util.List;

import com.mall.pojo.BrandExample;
import com.mall.pojo.BrandExample.Criteria;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.mall.mapper.BrandMapper;
import com.mall.pojo.Brand;
import com.mall.goods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import common.pojo.PageResult;
import com.mall.utils.StringUtils;

/**
 * 品牌服务实现
 * @author Wwl
 *
 */
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Override
	public void add(Brand brand) {
		brandMapper.insert(brand);
	}

	@Override
	public void update(Brand brand) {
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public Brand findOne(Long id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Brand> findAll() {
		return brandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Brand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		BrandExample example = new BrandExample();
		Criteria criteria = example.createCriteria();
		if(brand!=null){
			if(!StringUtils.isEmpty(brand.getName())){
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(!StringUtils.isEmpty(brand.getFirstChar())){
				criteria.andFirstCharEqualTo(brand.getFirstChar());
			}
		}
		Page<Brand> page = (Page<Brand>)brandMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			brandMapper.deleteByPrimaryKey(id);
		}
	}

}
