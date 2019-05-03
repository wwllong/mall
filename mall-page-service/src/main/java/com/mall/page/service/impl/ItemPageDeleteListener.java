package com.mall.page.service.impl;

import com.mall.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author Jack Wen
 * @className DeletePageListener
 * @dsecription 删除页面消息监听器
 * @data 2019/5/3 0003
 * @vserion 1.0
 */
@Component
public class ItemPageDeleteListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {

        //获取消息
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Long[] goodsIds = (Long[])objectMessage.getObject();
            //删除静态页
            for (Long goodsId : goodsIds){
                System.out.println("监听到删除页面消息:"+goodsId);
                itemPageService.deleteItemHtml(goodsId);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
