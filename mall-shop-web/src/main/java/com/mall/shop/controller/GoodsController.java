package com.mall.shop.controller;

import com.mall.goods.service.GoodsService;
import com.mall.pojo.Goods;
import com.mall.pojo.Item;
import com.mall.pojogroup.GoodsGroup;
import com.mall.search.service.ItemSearchService;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品SPU 控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    @Reference
    private ItemSearchService itemSearchService;

    /**
     * 新增
     *
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsGroup goodsGroup) {
        try {
            //获取登陆名称
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsGroup.getGoods().setSellerId(sellerId);

            goodsService.add(goodsGroup);

            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsGroup goodsGroup) {
        //判断商品是否为该商家的商品,且传过来的商家Id也要符合登陆用户的Id
        GoodsGroup goodsGroup2 = goodsService.findOne(goodsGroup.getGoods().getId());
        //获取登陆名称
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!goodsGroup2.getGoods().getSellerId().equals(sellerId) || !goodsGroup.getGoods().getSellerId().equals(sellerId)){
            return Result.error("非法操作");
        }
        try {
            goodsService.update(goodsGroup);
            //商品审核通过，更新solr索引库
            if(goodsGroup.getGoods().getAuditStatus().equals("1")){
                Long[] ids = new Long[1];
                ids[0] = goodsGroup.getGoods().getId();
                //删除原有索引
                itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
                //批量导入
                List<Item> itemList = goodsService.findItemListByGoodsIdAndStatus(ids, "1");
                if(itemList.size()>0){
                    itemSearchService.importList(itemList);
                }
            }
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 查找一条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public GoodsGroup findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Goods> findAll() {
        return goodsService.findAll();
    }

    /**
     * 列表查询-分页
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/listByPage")
    public PageResult findPage(int page, int size) {
        return goodsService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param goods
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int size) {
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //添加查询条件
        goods.setSellerId(sellerId);
        return goodsService.findPage(goods, page, size);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 批量修改状态
     *
     * @param ids
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status) {
        String[] statusArr = new String[]{"1","3"};
        if(statusArr[0].equals(status) || statusArr[1].equals(status)){
            return Result.error("非法操作!");
        }
        try {
            goodsService.updateStatus(ids,status);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 批量上下架
     *
     * @param ids
     * @return
     */
    @RequestMapping("/updateMarketable")
    public Result updateMarketable(Long[] ids,String status) {
        try {
            goodsService.updateMarketable(ids,status);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}