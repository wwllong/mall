package com.mall.page.service.impl;

import com.mall.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Arrays;

/**
 * @author Jack Wen
 * @className GenPageListener
 * @dsecription 生成页面消息监听器
 * @data 2019/5/3
 * @vserion 1.0
 */
@Component
public class ItemPageGenListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        //获取消息
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Long[] goodsIds = (Long[])objectMessage.getObject();
            for(long goodsId  : goodsIds) {
                //生成静态页面
                System.out.println("监听到生成页面消息:"+goodsId);
                itemPageService.genItemHtml(goodsId);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
