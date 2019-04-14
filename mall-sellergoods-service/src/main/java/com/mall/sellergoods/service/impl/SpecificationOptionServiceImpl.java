package com.mall.sellergoods.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.mapper.SpecificationOptionMapper;
import com.mall.pojo.SpecificationOption;
import com.mall.pojo.SpecificationOptionExample;
import com.mall.pojo.SpecificationOptionExample.Criteria;
import com.mall.sellergoods.service.SpecificationOptionService;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 规格服务实现
 * @author Wwl
 *
 */
@Service
public class SpecificationOptionServiceImpl implements SpecificationOptionService {

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	@Override
	public void add(SpecificationOption specificationOption) {
		specificationOptionMapper.insert(specificationOption);
	}

	@Override
	public void update(SpecificationOption specificationOption) {
		specificationOptionMapper.updateByPrimaryKey(specificationOption);
	}

	@Override
	public SpecificationOption findOne(Long id) {
		return specificationOptionMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SpecificationOption> findAll() {
		return specificationOptionMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<SpecificationOption> page = (Page<SpecificationOption>) specificationOptionMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(SpecificationOption specificationOption, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		SpecificationOptionExample example = new SpecificationOptionExample();
		Criteria criteria = example.createCriteria();
		if(specificationOption!=null){
			if(!StringUtils.isEmpty(specificationOption.getOptionName())){
				criteria.andOptionNameLike("%"+specificationOption.getOptionName()+"%");
			}
		}
		Page<SpecificationOption> page = (Page<SpecificationOption>)specificationOptionMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			specificationOptionMapper.deleteByPrimaryKey(id);
		}
	}

}
