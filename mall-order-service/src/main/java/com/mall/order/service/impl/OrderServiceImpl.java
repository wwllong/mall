package com.mall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.cart.service.CartService;
import com.mall.mapper.OrderItemMapper;
import com.mall.order.service.OrderService;
import com.mall.mapper.OrderMapper;
import com.mall.pojo.Order;
import com.mall.pojo.OrderExample;
import com.mall.pojo.OrderExample.Criteria;
import com.mall.pojo.OrderItem;
import com.mall.pojogroup.Cart;
import com.mall.utils.IdWorker;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单服务实现
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private OrderItemMapper orderItemMapper;

    @Reference
	private CartService cartService;

	/**
	 * 增加
	 * @param order
	 * @param payCartList 购物车对象
	 */
	@Override
	public void add(Order order,List<Cart> payCartList) {
//		if(payCartListJson==null || "".equals(payCartListJson)){
//			throw new RuntimeException("购物车为空");
//		}
//		//1. 获取用户订单购物车
//		List<Cart> payCartList = JSON.parseArray(payCartListJson, Cart.class);
//		if(payCartList.size()==0){
//			throw new RuntimeException("购物车为空");
//		}
		//1.用户Redis购物车,用于更新
		List<Cart> cartList = (List<Cart>)redisTemplate.boundHashOps("cartList").get(order.getUserId());
		//2. 生成订单和订单项
		for(Cart cart : payCartList){
			//2.1生成订单ID
			long orderId = idWorker.nextId();
			//2.2创建新订单和赋值
			Order newOrder = new Order();
			newOrder.setOrderId(orderId);
			newOrder.setPaymentType(order.getPaymentType());
			newOrder.setStatus("1");
			Date date = new Date();
			newOrder.setCreateTime(date);
			newOrder.setUpdateTime(date);
			newOrder.setUserId(order.getUserId());
			newOrder.setReceiverAreaName(order.getReceiverAreaName());
			newOrder.setReceiverMobile(order.getReceiverMobile());
			newOrder.setReceiver(order.getReceiver());
			newOrder.setReceiver(order.getReceiver());
			newOrder.setSourceType(order.getSourceType());
			newOrder.setSellerId(order.getSellerId());
			//2.3 生成订单明细
			double payment = 0;
			for(OrderItem orderItem : cart.getOrderItemList()){
				orderItem.setId(idWorker.nextId());
				orderItem.setOrderId(orderId);
				orderItem.setSellerId(cart.getSellerId());
				//2.4 保存订单明细
				orderItemMapper.insert(orderItem);
				//合计订单总金额
				payment += orderItem.getTotalFee().doubleValue();
			}
			newOrder.setPayment(new BigDecimal(payment));
			//2.5 保存订单
			orderMapper.insert(newOrder);
		}
		//3. 更新Redis购物车-单独执行，避免之前的事务失败
		for(Cart cart : payCartList){
			for(OrderItem orderItem : cart.getOrderItemList()){
				cartList = cartService.addGoods2CarList(orderItem.getItemId(),-orderItem.getNum(),cartList);
			}
		}
        cartService.saveCartList2Redis(order.getUserId(),cartList);
	}

	@Override
	public void update(Order order) {
		orderMapper.updateByPrimaryKey(order);
	}

	@Override
	public Order findOne(Long id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Order> findAll() {
		return orderMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<Order> page = (Page<Order>) orderMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(Order order, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		if(order!=null){
			if(!StringUtils.isEmpty(order.getPaymentType())){
				criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
			}
			if(!StringUtils.isEmpty(order.getPostFee())){
				criteria.andPostFeeLike("%"+order.getPostFee()+"%");
			}
			if(!StringUtils.isEmpty(order.getStatus())){
				criteria.andStatusLike("%"+order.getStatus()+"%");
			}
			if(!StringUtils.isEmpty(order.getShippingName())){
				criteria.andShippingNameLike("%"+order.getShippingName()+"%");
			}
			if(!StringUtils.isEmpty(order.getShippingCode())){
				criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
			}
			if(!StringUtils.isEmpty(order.getUserId())){
				criteria.andUserIdLike("%"+order.getUserId()+"%");
			}
			if(!StringUtils.isEmpty(order.getBuyerMessage())){
				criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
			}
			if(!StringUtils.isEmpty(order.getBuyerNick())){
				criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
			}
			if(!StringUtils.isEmpty(order.getBuyerRate())){
				criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
			}
			if(!StringUtils.isEmpty(order.getReceiverAreaName())){
				criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
			}
			if(!StringUtils.isEmpty(order.getReceiverMobile())){
				criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
			}
			if(!StringUtils.isEmpty(order.getReceiverZipCode())){
				criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
			}
			if(!StringUtils.isEmpty(order.getReceiver())){
				criteria.andReceiverLike("%"+order.getReceiver()+"%");
			}
			if(!StringUtils.isEmpty(order.getInvoiceType())){
				criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
			}
			if(!StringUtils.isEmpty(order.getSourceType())){
				criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
			}
			if(!StringUtils.isEmpty(order.getSellerId())){
				criteria.andSellerIdLike("%"+order.getSellerId()+"%");
			}
		}
		Page<Order> page = (Page<Order>)orderMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			orderMapper.deleteByPrimaryKey(id);
		}
	}


}
