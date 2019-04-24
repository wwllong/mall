package com.mall.search.service.impl;

import com.mall.pojo.Item;
import com.mall.search.service.ItemSearchService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Wen
 * @className ItemSearchServiceImpl
 * @dsecription 商品SKU搜索服务实现
 * @data 2019/4/25 0025
 * @vserion 1.0
 */

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> serach(Map searchMap) {
        Map<String, Object> resultMap = new HashMap<>();

        //查询条件
        Query query = new SimpleQuery();
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //分页
        ScoredPage<Item> page = solrTemplate.queryForPage(query, Item.class);
        resultMap.put("rows",page.getContent());
        return resultMap;
    }
}
