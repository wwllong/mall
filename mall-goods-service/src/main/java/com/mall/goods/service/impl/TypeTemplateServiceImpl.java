package com.mall.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.TypeTemplateService;
import com.mall.mapper.TypeTemplateMapper;
import com.mall.pojo.TypeTemplate;
import com.mall.pojo.TypeTemplateExample;
import com.mall.pojo.TypeTemplateExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品类型模板服务
 * @author Wwl
 *
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TypeTemplateMapper typeTemplateMapper;

	@Override
	public void add(TypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);
	}

	@Override
	public void update(TypeTemplate typeTemplate) {
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}

	@Override
	public TypeTemplate findOne(Long id) {
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		TypeTemplateExample example = new TypeTemplateExample();
		Criteria criteria = example.createCriteria();
		if(typeTemplate!=null){
			if(!StringUtils.isEmpty(typeTemplate.getName())){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
		}
		Page<TypeTemplate> page = (Page<TypeTemplate>)typeTemplateMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}
	}

}
