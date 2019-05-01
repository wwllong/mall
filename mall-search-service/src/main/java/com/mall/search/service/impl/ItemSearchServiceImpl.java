package com.mall.search.service.impl;

import com.mall.pojo.Item;
import com.mall.search.service.ItemSearchService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.*;

/**
 * @author Jack Wen
 * @className ItemSearchServiceImpl
 * @dsecription 商品SKU搜索服务实现
 * @data 2019/4/25 0025
 * @vserion 1.0
 */

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    enum SearchEnum{
        /**查询条件枚举*/
        KEYWORDS,CATEGORY,BRAND,SPEC,PRICE,
        PAGE_NUMBER,PAGE_SIZE,
        SORT,SORT_FIELD
    }

    /**
     * 查询条件map -> key ,value
     */
    private Map<SearchEnum,String> searchCriteria = new EnumMap(SearchEnum.class);

    {
        searchCriteria.put(SearchEnum.KEYWORDS,"keywords");
        searchCriteria.put(SearchEnum.CATEGORY,"category");
        searchCriteria.put(SearchEnum.BRAND,"brand");
        searchCriteria.put(SearchEnum.SPEC,"spec");
        searchCriteria.put(SearchEnum.PRICE,"price");
        searchCriteria.put(SearchEnum.PAGE_NUMBER,"pageNumber");
        searchCriteria.put(SearchEnum.PAGE_SIZE,"pageSize");
        searchCriteria.put(SearchEnum.SORT,"sort");
        searchCriteria.put(SearchEnum.SORT_FIELD,"sortField");
    }

    private final String EMPTY_STR = "";

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询入口
     * @param searchMap 关键字Map集合
     * @return
     */
    @Override
    public Map<String, Object> search(Map searchMap) {
        if(EMPTY_STR.equals( searchMap.get(searchCriteria.get(SearchEnum.KEYWORDS)) )){
            return null;
        }else{
            //关键字处理
            String keywords = (String)searchMap.get(searchCriteria.get(SearchEnum.KEYWORDS));
            searchMap.put(searchCriteria.get(SearchEnum.KEYWORDS),keywords.replace(" ",""));

        }
        Map<String, Object> resultMap = new HashMap<>();

        /*** 1.关键字高亮查询 ***/
        resultMap.putAll(searchList(searchMap));

        /*** 2.查询分类列表 ***/
        List<String> categoryList = searchCategoryList(searchMap);
        resultMap.put("categoryList",categoryList);

        /*** 3.查询品牌和规格列表 ***/
        String category = searchCriteria.get(SearchEnum.CATEGORY);
        if(!EMPTY_STR.equals(searchMap.get(category))){
            resultMap.putAll(searchBrandAndSpecList((String)searchMap.get(category)));
        }else{
            if(categoryList.size()>0){
                //默认查询第一个分类的品牌和规格列表
                resultMap.putAll(searchBrandAndSpecList(categoryList.get(0)));
            }
        }

        return resultMap;
    }

    /**
     * 关键字高亮查询
     * @param searchMap 查询关键字多元集合
     * @return Map 查询结果集
     */
    private Map searchList(Map searchMap){
        Map resultMap = new HashMap();

        /*===1.1关键字&高亮查询===*/
        //高亮查询条件
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        HighlightOptions highLightOptions = new HighlightOptions().addField("item_title");
        highLightOptions.setSimplePrefix("<span style='color:red'>");
        highLightOptions.setSimplePostfix("</span>");
        query.setHighlightOptions(highLightOptions);
        //关键字查询条件
        String keywords = searchCriteria.get(SearchEnum.KEYWORDS);
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get(keywords));
        query.addCriteria(criteria);

        //查询过滤
        searchFilter(searchMap,query);

        /*===1.6分页查询===*/
        String pageNumStr = searchCriteria.get(SearchEnum.PAGE_NUMBER);
        String pageSizeStr = searchCriteria.get(SearchEnum.PAGE_SIZE);
        //页码
        Integer pageNumber = (Integer)searchMap.get(pageNumStr);
        if(pageNumber==null){
            pageNumber = 1;
        }
        //每页记录
        Integer pageSize = (Integer)searchMap.get(pageSizeStr);
        if(pageSize==null){
            pageSize = 20;
        }
        query.setOffset((pageNumber-1)*pageSize);
        query.setRows(pageSize);

        /*===1.7排序===*/
        String sort = searchCriteria.get(SearchEnum.SORT);
        String sortValue = (String)searchMap.get(sort);
        if(sortValue!=null && !EMPTY_STR.equals(sortValue)){
            String sortField = searchCriteria.get(SearchEnum.SORT_FIELD);
            String sortFieldVal = (String)searchMap.get(sortField);
            if("ASC".equals(sortValue)){
                Sort orders = new Sort(Sort.Direction.ASC, "item_" + sortFieldVal);
                query.addSort(orders);
            }
            if("DESC".equals(sortValue)){
                Sort orders = new Sort(Sort.Direction.DESC, "item_" + sortFieldVal);
                query.addSort(orders);
            }
        }



        /*===1.8执行查询、设置高亮===*/
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage(query, Item.class);
        page.getHighlighted().forEach( (highLightEntry)->{
            //所有高亮域
            List<HighlightEntry.Highlight> highLights = highLightEntry.getHighlights();
            Item item = highLightEntry.getEntity();
            if(highLights !=null && highLights.size() > 0 && highLights.get(0).getSnipplets().size()>0 ){
                //第一个域的第一个高亮
                item.setTitle(highLights.get(0).getSnipplets().get(0));
            }
        });

        /*===1.9结果封装===*/
        resultMap.put("rows",page.getContent());
        resultMap.put("totalPages",page.getTotalPages());
        resultMap.put("total",page.getTotalElements());

        return resultMap;
    }

    private void searchFilter(Map searchMap,Query query){
        /*===1.2分类过滤===*/
        String category = searchCriteria.get(SearchEnum.CATEGORY);
        if(!EMPTY_STR.equals(searchMap.get(category))){
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get(category));
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        /*===1.3品牌过滤===*/
        String brand = searchCriteria.get(SearchEnum.BRAND);
        if(!EMPTY_STR.equals(searchMap.get(brand))){
            Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get(brand));
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        /*===1.4规格过滤===*/
        String spec = searchCriteria.get(SearchEnum.SPEC);
        if(null!=searchMap.get(spec)){
            Map<String,String> specMap =(Map) searchMap.get(spec);
            Set<Map.Entry<String, String>> entries = specMap.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                Criteria filterCriteria = new Criteria("item_spec_" + next.getKey()).is(next.getValue());
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        /*===1.5价格过滤===*/
        String price = searchCriteria.get(SearchEnum.PRICE);
        if(!EMPTY_STR.equals(searchMap.get(price))){
            //价格区间
            String[] priceSection = ((String)searchMap.get(price)).split("-");
            if(!priceSection[0].equals("0")){
                Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(priceSection[0]);
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if(!priceSection[1].equals("*")){
                Criteria filterCriteria = new Criteria("item_price").lessThanEqual(priceSection[1]);
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
    }

    /**
     * 查询商品分类列表
     * @param searchMap
     * @return
     */
    private List<String> searchCategoryList(Map searchMap){
        ArrayList<String> list = new ArrayList<>();

        //关键字查询
        Query query = new SimpleQuery();
        String keywords = searchCriteria.get(SearchEnum.KEYWORDS);
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get(keywords));
        query.addCriteria(criteria);
        //按分类域，分组查询
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        //分组结果
        GroupPage<Item> page = solrTemplate.queryForGroupPage(query, Item.class);
        //根据列获取分组结果
        GroupResult<Item> groupResult = page.getGroupResult("item_category");
        //分组结果入口
        List<GroupEntry<Item>> content = groupResult.getGroupEntries().getContent();
        for ( GroupEntry<Item> entry: content){
            //结果名称封装
            list.add(entry.getGroupValue());
        }

        return list;
    }

    /**
     * 根据分类名称查询品牌和规格列表
     * @param category
     * @return
     */
    private Map searchBrandAndSpecList(String category){
        Map resultMap = new HashMap();
        Long typeTemplateId = (Long)redisTemplate.boundHashOps("itemCatalog").get(category);
        if(typeTemplateId!=null){
            //查询品牌列表
            List brandList = (List)redisTemplate.boundHashOps("brandList").get(typeTemplateId);
            resultMap.put("brandList",brandList);
            //查询规格列表
            List specList = (List)redisTemplate.boundHashOps("specList").get(typeTemplateId);
            resultMap.put("specList",specList);
        }
        return resultMap;
    }

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    @Override
    public void deleteByGoodsIds(List goodsIdList) {
        Query query = new SimpleQuery();
        Criteria criteria = new Criteria("item_goodsid").is(goodsIdList);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
