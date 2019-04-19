package com.mall.manager.controller;

import com.mall.goods.service.SellerService;
import com.mall.pojo.Seller;
import common.pojo.Message;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 卖家控制层
 * @author Wwl
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
	
	@Reference
	private SellerService sellerService;

	/**
	 * 新增
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Seller seller){
		Seller isRegister = sellerService.findOne(seller.getSellerId());
		if(isRegister!=null){
			return new Result(false,"该账号已存在,请勿重复申请入驻！");
		}
		//密码加密
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptPassword = passwordEncoder.encode(seller.getPassword());
		seller.setPassword(encryptPassword);

		try {
			sellerService.add(seller);
			return  new Result(true, Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return  new Result(false,Message.ADMIN_FAIL);
		}

	}

	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("update")
	public Result update(@RequestBody Seller seller){
		try {
			sellerService.update(seller);
			return new Result(true,Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}

	/**
	 * 查找一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping("findOne")
	public Seller findOne(String id){
		return sellerService.findOne(id);
	}

	/**
	 * 列表查询
	 * @return
	 */
	@RequestMapping("/list")
	public List<Seller> findAll(){
		return sellerService.findAll();
	}

	/**
	 * 列表查询-分页
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/listByPage")
	public PageResult findPage(int page, int size){
		return sellerService.findPage(page, size);
	}

	/**
	 * 条件查询-分页
	 * @param seller
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody Seller seller, int page, int size){
		return sellerService.findPage(seller, page, size);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	public Result delete(String[] ids){
		try {
			sellerService.delete(ids);
			return new Result(true,Message.ADMIN_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}

	@RequestMapping("updateStatus")
	public Result updateStatus(String sellerId,String status){
		try {
			sellerService.updateStatus(sellerId,status);
			return new Result(true,Message.ADMIN_SUCCESS);
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,Message.ADMIN_FAIL);
		}
	}

}