package com.mall.search.service;

import java.util.List;
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
    public Map<String,Object> search(Map searchMap);


    /**
     * 导入数据
     * @param list Item List
     */
    public void importList(List list);

    /**
     * 删除数据
     * @param goodsIdList
     */
    public void deleteByGoodsIds(List goodsIdList);
}
