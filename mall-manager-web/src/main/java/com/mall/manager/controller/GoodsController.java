package com.mall.manager.controller;

import com.alibaba.fastjson.JSON;
import com.mall.goods.service.GoodsService;
import com.mall.pojo.Goods;
import com.mall.pojo.Item;
import com.mall.pojogroup.GoodsGroup;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
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

    /** 添加索引，消息队列*/
    @Autowired
    private Destination queueSolrDestination;

    /** 删除索引，消息队列*/
    @Autowired
    private Destination queueSolrDeleteDestination;

    /** 生成静态页面，消息主题*/
    @Autowired
    private Destination topicPageDestination;

    /** 删除静态页面，消息主题*/
    @Autowired
    private Destination topicPageDeleteDestination;

    @Autowired
    private JmsTemplate jmsTemplate;


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
            //消息生产者-发送删除索引消息
            jmsTemplate.send(queueSolrDeleteDestination, session -> session.createObjectMessage(ids));
            //消息生产者-发送删除静态页面的消息
            jmsTemplate.send(topicPageDeleteDestination, session -> session.createObjectMessage(ids));
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
        try {
            goodsService.updateStatus(ids,status);
            //更新索引库
            if("1".equals(status)){
                List<Item> itemList = goodsService.findItemListByGoodsIdAndStatus(ids, status);
                //批量导入Solr
                if(itemList.size()>0){
                    String jsonStr = JSON.toJSONString(itemList);
                    //消息生产者-发送导入数据消息
                    jmsTemplate.send(queueSolrDestination, session -> session.createTextMessage(jsonStr));
                }
            }
            //消息生产者-发送静态页面消息,生成静态页面
            jmsTemplate.send(topicPageDestination, session -> session.createObjectMessage(ids));

            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }



}