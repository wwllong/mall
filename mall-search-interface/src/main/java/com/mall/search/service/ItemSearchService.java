package com.mall.search.service;

import java.util.Map;

/**
 * @author Jack Wen
 * @className ItemSearchService
 * @dsecription 商品SKU搜索服务
 * @data 2019/4/25 0025
 * @vserion 1.0
 */

public interface ItemSearchService {

    /**
     * 搜索
     * @param searchMap 关键字Map集合
     * @return Map 返回多元化结果
     */
    public Map<String,Object> serach(Map searchMap);


}
