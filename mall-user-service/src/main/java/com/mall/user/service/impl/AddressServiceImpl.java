package com.mall.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.user.service.AddressService;
import com.mall.mapper.AddressMapper;
import com.mall.pojo.Address;
import com.mall.pojo.AddressExample;
import com.mall.pojo.AddressExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货地址服务实现
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressMapper addressMapper;

	@Override
	public void add(Address address) {
		addressMapper.insert(address);
	}

	@Override
	public void update(Address address) {
		addressMapper.updateByPrimaryKey(address);
	}

	@Override
	public Address findOne(Long id) {
		return addressMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Address> findAll() {
		return addressMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Address> page = (Page<Address>) addressMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Address address, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		AddressExample example = new AddressExample();
		Criteria criteria = example.createCriteria();
		if(address!=null){
			if(!StringUtils.isEmpty(address.getUserId())){
				criteria.andUserIdLike("%"+address.getUserId()+"%");
			}
			if(!StringUtils.isEmpty(address.getProvinceId())){
				criteria.andProvinceIdLike("%"+address.getProvinceId()+"%");
			}
			if(!StringUtils.isEmpty(address.getCityId())){
				criteria.andCityIdLike("%"+address.getCityId()+"%");
			}
			if(!StringUtils.isEmpty(address.getTownId())){
				criteria.andTownIdLike("%"+address.getTownId()+"%");
			}
			if(!StringUtils.isEmpty(address.getMobile())){
				criteria.andMobileLike("%"+address.getMobile()+"%");
			}
			if(!StringUtils.isEmpty(address.getAddress())){
				criteria.andAddressLike("%"+address.getAddress()+"%");
			}
			if(!StringUtils.isEmpty(address.getContact())){
				criteria.andContactLike("%"+address.getContact()+"%");
			}
			if(!StringUtils.isEmpty(address.getIsDefault())){
				criteria.andIsDefaultLike("%"+address.getIsDefault()+"%");
			}
			if(!StringUtils.isEmpty(address.getNotes())){
				criteria.andNotesLike("%"+address.getNotes()+"%");
			}
			if(!StringUtils.isEmpty(address.getAlias())){
				criteria.andAliasLike("%"+address.getAlias()+"%");
			}
		}
		Page<Address> page = (Page<Address>)addressMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			addressMapper.deleteByPrimaryKey(id);
		}
	}

	/**
	 * 根据用户ID查询用户送货地址
	 *
	 * @param userId 用户ID
	 * @return 用户送货地址
	 */
	@Override
	public List<Address> findListByUserId(String userId) {
		AddressExample example = new AddressExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return addressMapper.selectByExample(example);
	}

}
