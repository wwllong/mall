package com.mall.search.controller;

import com.mall.search.service.ItemSearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Jack Wen
 * @className ItemSearch
 * @dsecription 商品SKU搜索控制层
 * @data 2019/4/25 0025
 * @vserion 1.0
 */

@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {

    @Reference
    private ItemSearchService itemSearchService;

    /**
     *
     * @param searchMap 查询关键字结果集
     * @return 查询结果多元集合
     */
    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map searchMap){
         return itemSearchService.serach(searchMap);
    }

}
