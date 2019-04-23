package com.mall.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.goods.service.GoodsService;
import com.mall.mapper.*;
import com.mall.pojo.*;
import com.mall.pojo.GoodsExample.Criteria;
import com.mall.pojogroup.GoodsGroup;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * 商品SPU服务实现
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private GoodsDescMapper goodsDescMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemCatalogMapper itemCatalogMapper;

	@Autowired
	private SellerMapper sellerMapper;

	@Autowired
	private BrandMapper brandMapper;

	@Override
	public void add(GoodsGroup goodsGroup) {
		//插入商品SUP，goods表
		Goods goods = goodsGroup.getGoods();
		goods.setAuditStatus("0");
		goodsMapper.insert(goods);
		//插入商品SUP扩展，goods_des表
		GoodsDesc goodsDesc = goodsGroup.getGoodsDesc();
		goodsDesc.setGoodsId(goods.getId());
		goodsDescMapper.insert(goodsDesc);
        insertItemList(goodsGroup);
	}

    /**
     * 设置item的实体的值
     * @param goodsGroup
     * @param item
     */
	private void setItemValues(GoodsGroup goodsGroup,Item item){
		Goods goods = goodsGroup.getGoods();
		GoodsDesc goodsDesc = goodsGroup.getGoodsDesc();

		//商品图片（第一个图片）
		List<Map> imageList = JSON.parseArray(goodsDesc.getItemImages(),Map.class);
		if(imageList.size()>0){
			item.setImage((String)imageList.get(0).get("url"));
		}

		//所属类目
		item.setCategoryId(goods.getCategory3Id());
		item.setCategoryId(goods.getCategory3Id());
		ItemCatalog itemCatalog = itemCatalogMapper.selectByPrimaryKey(goods.getCategory3Id());
		item.setCategory(itemCatalog.getName());

		//时间
		Date date = new Date();
		item.setCreateTime(date);
		item.setUpdateTime(date);
		//属于的SPU,填ID
		item.setGoodsId(goods.getId());

		//商家ID、店铺昵称
		item.setSellerId(goods.getSellerId());
		Seller seller = sellerMapper.selectByPrimaryKey(goods.getSellerId());
		item.setSeller(seller.getNickName());
		//品牌名称
		Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
		item.setBrand(brand.getName());
	}

	@Override
	public void update(GoodsGroup goodsGroup) {
		goodsMapper.updateByPrimaryKey(goodsGroup.getGoods());
		goodsDescMapper.updateByPrimaryKey(goodsGroup.getGoodsDesc());
		//删除原有SKU列表数据
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsGroup.getGoods().getId());
        itemMapper.deleteByExample(example);
        //添加新的SKU列表数据
        insertItemList(goodsGroup);
	}

    /**
     * 插入item列表数据
     */
	private void insertItemList(GoodsGroup goodsGroup){
	    Goods goods = goodsGroup.getGoods();
        String ENABLE_SPEC = "1";
        if(ENABLE_SPEC.equals(goods.getIsEnableSpec()))	{
            //插入商品SKU，item表
            for(Item item : goodsGroup.getItemList()) {
                String title = goods.getGoodsName();
                //商品KPU+规格描述串作为SKU名称
                Map<String,Object> specMap = JSON.parseObject(item.getSpec());
                for(Iterator<Map.Entry<String, Object>> iterator = specMap.entrySet().iterator(); iterator.hasNext(); ){
                    Map.Entry<String, Object> next = iterator.next();
                    title += " "+ next.getValue();
                }
                item.setTitle(title);
                setItemValues(goodsGroup,item);
                itemMapper.insert(item);
            }
        }else{
            Item item=new Item();
            item.setTitle(goods.getGoodsName());
            //单条SKU信息
            item.setPrice(goods.getPrice());
            item.setStatus("1");
            item.setIsDefault("1");
            item.setNum(9999);
            item.setSpec("{}");
            setItemValues(goodsGroup,item);
            itemMapper.insert(item);
        }
    }

	@Override
	public GoodsGroup findOne(Long id) {
		GoodsGroup goodsGroup = new GoodsGroup();
		//获取商品SPU信息
		Goods goods = goodsMapper.selectByPrimaryKey(id);
		goodsGroup.setGoods(goods);
		//获取商品SPU扩展信息
		GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goodsGroup.setGoodsDesc(goodsDesc);

		//读取SKU信息
		ItemExample example = new ItemExample();
		ItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);

		List<Item> itemList = itemMapper.selectByExample(example);
		goodsGroup.setItemList(itemList);
		return goodsGroup;
	}

	@Override
	public List<Goods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Goods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		if(goods!=null){
			criteria.andIsDeleteIsNull();
			if(!StringUtils.isEmpty(goods.getSellerId())){
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(!StringUtils.isEmpty(goods.getGoodsName())){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(!StringUtils.isEmpty(goods.getAuditStatus())){
				criteria.andAuditStatusEqualTo(goods.getAuditStatus());
			}
			if(!StringUtils.isEmpty(goods.getIsMarketable())){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(!StringUtils.isEmpty(goods.getCaption())){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(!StringUtils.isEmpty(goods.getSmallPic())){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(!StringUtils.isEmpty(goods.getIsEnableSpec())){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
		}
		Page<Goods> page = (Page<Goods>)goodsMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		//逻辑删除
		Goods goods = new Goods();
		goods.setIsDelete("1");
		for(Long id : ids){
			goods.setId(id);
			goodsMapper.updateByPrimaryKeySelective(goods);
		}
	}

	@Override
	public void updateMarketable(Long[] ids,String status) {
		Goods goods = new Goods();
		goods.setIsMarketable(status);
		for(Long id : ids){
			goods.setId(id);
			goodsMapper.updateByPrimaryKeySelective(goods);
		}
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		Goods goods = new Goods();
		goods.setAuditStatus(status);
		for(Long id : ids){
			goods.setId(id);
			goodsMapper.updateByPrimaryKeySelective(goods);
		}
	}


}
