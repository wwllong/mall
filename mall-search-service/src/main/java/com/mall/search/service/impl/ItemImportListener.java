package com.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.mall.pojo.Item;
import com.mall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

/**
 * @author Jack Wen
 * @className ItemSearchListener
 * @dsecription 商品搜索消息监听-导入索引库
 * @data 2019/5/3 0003
 * @vserion 1.0
 */

@Component
public class ItemImportListener implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println("监听到导入索引库消息："+text);
            //转换为导入数据
            List<Item> itemList = JSON.parseArray(text, Item.class);
            itemList.forEach((item)->{
                //将spec字段，json字符串转换为map
                Map specMap = JSON.parseObject(item.getSpec());
                item.setSpecMap(specMap);
            });
            //导入索引库
            itemSearchService.importList(itemList);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
