package com.mall.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.TypeTemplateService;
import com.mall.mapper.SpecificationOptionMapper;
import com.mall.mapper.TypeTemplateMapper;
import com.mall.pojo.SpecificationOption;
import com.mall.pojo.SpecificationOptionExample;
import com.mall.pojo.TypeTemplate;
import com.mall.pojo.TypeTemplateExample;
import com.mall.pojo.TypeTemplateExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品类型模板服务
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TypeTemplateMapper typeTemplateMapper;

	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void add(TypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);
		saveToRedis();
	}

	@Override
	public void update(TypeTemplate typeTemplate) {
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
		saveToRedis();
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
		//缓存品牌和规格列表
		if(redisTemplate.boundHashOps("brandList").size()==0 || redisTemplate.boundHashOps("specList").size()==0 ){
			saveToRedis();
		}
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}
		saveToRedis();
	}

	@Override
	public List<Map> findOptionList() {
		return typeTemplateMapper.selectOptionList();
	}

	@Override
	public List<Map> findSpecListWithOptions(Long id) {
		// 查询模板
		TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		//JSON字符串转换
		List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);

		// 查询规格选项列表
		list.forEach( map -> {
			SpecificationOptionExample example = new SpecificationOptionExample();
			SpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(Long.parseLong(map.get("id").toString()));
			List<SpecificationOption> options = specificationOptionMapper.selectByExample(example);
			map.put("options",options);
		});

		return list;
	}

	/**
	 * 缓存模板的品牌和规格列表
	 */
	private void saveToRedis(){
		for(TypeTemplate typeTemplate : findAll()){
			Long typeId = typeTemplate.getId();
			//缓存品牌 brandList：[{typeID : [{id,text}] }]
			List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
			redisTemplate.boundHashOps("brandList").put(typeId,brandList);
			//缓存规格 specList：[{typeID : [{id,text,[options]}] }]
			List<Map> specList = findSpecListWithOptions(typeId);
			redisTemplate.boundHashOps("specList").put(typeId,specList);
		}

	}

}
