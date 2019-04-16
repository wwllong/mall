package com.mall.goods.service.impl;

import com.mall.pojo.SpecificationOptionExample;
import org.apache.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.mapper.SpecificationMapper;
import com.mall.mapper.SpecificationOptionMapper;
import com.mall.pojo.Specification;
import com.mall.pojo.SpecificationExample;
import com.mall.pojo.SpecificationExample.Criteria;
import com.mall.pojo.SpecificationOption;
import com.mall.pojogroup.SpecificationGroup;
import com.mall.goods.service.SpecificationService;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 规格服务实现
 * @author Wwl
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationMapper specificationMapper;

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	@Override
	public void add(SpecificationGroup specificationGroup) {

		Specification specification = specificationGroup.getSpecification();
		//添加规格
		specificationMapper.insert(specification);
		Long specId = specification.getId();
		//添加规格详细
		for(SpecificationOption specOption : specificationGroup.getSpecificationOptionList()){
			specOption.setSpecId(specId);
			specificationOptionMapper.insert(specOption);
		}

	}

	@Override
	public void update(Specification specification) {
		specificationMapper.updateByPrimaryKey(specification);
	}

	@Override
	public SpecificationGroup findOne(Long id) {
		SpecificationGroup specificationGroup = new SpecificationGroup();
		specificationGroup.setSpecification(specificationMapper.selectByPrimaryKey(id));

		//规格详细
		SpecificationOptionExample example = new SpecificationOptionExample();
		SpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<SpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);
		specificationGroup.setSpecificationOptionList(specificationOptionList);

		return specificationGroup;
	}

	@Override
	public List<Specification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Specification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		SpecificationExample example = new SpecificationExample();
		Criteria criteria = example.createCriteria();
		if(specification!=null){
			if(!StringUtils.isEmpty(specification.getSpecName())){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
		}
		Page<Specification> page = (Page<Specification>)specificationMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			specificationMapper.deleteByPrimaryKey(id);
		}
	}

}
