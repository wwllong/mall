package com.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.mall.pojo.Item;
import com.mall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jack Wen
 * @className ItemDeleteListener
 * @dsecription 商品搜索消息监听-删除索引库
 * @data 2019/5/3 0003
 * @vserion 1.0
 */

@Component
public class ItemDeleteListener implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {

        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            //获取消息
            Long[] goodsIds = (Long[])objectMessage.getObject();
            System.out.println("监听到删除索引库消息："+goodsIds);
            //删除索引库
            itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
