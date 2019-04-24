package com.mall.solrutil;

import com.alibaba.fastjson.JSON;
import com.mall.mapper.ItemMapper;
import com.mall.pojo.Item;
import com.mall.pojo.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Jack Wen
 * @className SolrUtil
 * @dsecription Solr导入数据工具
 * @data 2019/4/25 0025
 * @vserion 1.0
 */

@Component
public class SolrUtil {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 导入商品数据
     */
    public void importItemData(){
        //查询要导入的数据
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<Item> itemList = itemMapper.selectByExample(example);

        itemList.forEach( (item) -> {
            //规格动态域
            Map specMap = JSON.parseObject(item.getSpec());
            item.setSpecMap(specMap);
            System.out.println(item.getTitle());
        });

        solrTemplate.saveBeans(itemList);

        solrTemplate.commit();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil)context.getBean("solrUtil");
        solrUtil.importItemData();
    }
}
