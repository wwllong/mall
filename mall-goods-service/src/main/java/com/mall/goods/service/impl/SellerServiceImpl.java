package com.mall.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.SellerService;
import com.mall.mapper.SellerMapper;
import com.mall.pojo.Seller;
import com.mall.pojo.SellerExample;
import com.mall.pojo.SellerExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商家/卖家服务实现
 * @author Wwl
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerMapper sellerMapper;

	@Override
	public void add(Seller seller) {
		seller.setCreateTime(new Date());
		sellerMapper.insert(seller);
	}

	@Override
	public void update(Seller seller) {
		sellerMapper.updateByPrimaryKey(seller);
	}

	@Override
	public Seller findOne(String sellerId) {
		return sellerMapper.selectByPrimaryKey(sellerId);
	}

	@Override
	public List<Seller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Seller> page = (Page<Seller>) sellerMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Seller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		SellerExample example = new SellerExample();
		Criteria criteria = example.createCriteria();
		if(seller!=null){
			if(!StringUtils.isEmpty(seller.getSellerId())){
				criteria.andSellerIdLike("%"+seller.getSellerId()+"%");
			}
			if(!StringUtils.isEmpty(seller.getName())){
				criteria.andNameLike("%"+seller.getName()+"%");
			}
			if(!StringUtils.isEmpty(seller.getNickName())){
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}
			if(!StringUtils.isEmpty(seller.getPassword())){
				criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}
			if(!StringUtils.isEmpty(seller.getEmail())){
				criteria.andEmailLike("%"+seller.getEmail()+"%");
			}
			if(!StringUtils.isEmpty(seller.getMobile())){
				criteria.andMobileLike("%"+seller.getMobile()+"%");
			}
			if(!StringUtils.isEmpty(seller.getTelephone())){
				criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}
			if(!StringUtils.isEmpty(seller.getStatus())){
				criteria.andStatusLike("%"+seller.getStatus()+"%");
			}
			if(!StringUtils.isEmpty(seller.getAddressDetail())){
				criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLinkmanName())){
				criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLinkmanQq())){
				criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLinkmanMobile())){
				criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLinkmanEmail())){
				criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLicenseNumber())){
				criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}
			if(!StringUtils.isEmpty(seller.getTaxNumber())){
				criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}
			if(!StringUtils.isEmpty(seller.getOrgNumber())){
				criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLogoPic())){
				criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}
			if(!StringUtils.isEmpty(seller.getBrief())){
				criteria.andBriefLike("%"+seller.getBrief()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLegalPerson())){
				criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}
			if(!StringUtils.isEmpty(seller.getLegalPersonCardId())){
				criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}
			if(!StringUtils.isEmpty(seller.getBankUser())){
				criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}
			if(!StringUtils.isEmpty(seller.getBankName())){
				criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}
		}
		Page<Seller> page = (Page<Seller>)sellerMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(String[] ids) {
		for(String id : ids){
			sellerMapper.deleteByPrimaryKey(id);
		}
	}


}
