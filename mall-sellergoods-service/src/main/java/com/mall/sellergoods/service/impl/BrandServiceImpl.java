package com.mall.sellergoods.service.impl;

import java.util.List;

import com.mall.pojo.BrandExample;
import com.mall.pojo.BrandExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mall.dao.BrandDao;
import com.mall.pojo.Brand;
import com.mall.sellergoods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import common.pojo.PageResult;
import com.mall.utils.StringUtils;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;

	/**增加*/
	@Override
	public void add(Brand brand) {
		brandDao.insert(brand);
	}

	/**更新*/
	@Override
	public void update(Brand brand) {
		brandDao.updateByPrimaryKey(brand);
	}

	/**根据ID获取实体*/
	@Override
	public Brand findOne(Long id) {
		return brandDao.selectByPrimaryKey(id);
	}

	/** 品牌列表*/
	@Override
	public List<Brand> findAll() {
		return brandDao.selectByExample(null);
	}

	/** 品牌列表-分页*/
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		//分页
		PageHelper.startPage(pageNum, pageSize);

		Page<Brand> page = (Page<Brand>) brandDao.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	/** 品牌列表查询-分页*/
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
		Page<Brand> page = (Page<Brand>)brandDao.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	/** 删除 */
	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			brandDao.deleteByPrimaryKey(id);
		}
	}

}
