package com.mall.page.service.impl;

import com.mall.mapper.GoodsDescMapper;
import com.mall.mapper.GoodsMapper;
import com.mall.mapper.ItemCatalogMapper;
import com.mall.mapper.ItemMapper;
import com.mall.page.service.ItemPageService;
import com.mall.pojo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack Wen
 * @className ItemPageServiceImpl
 * @dsecription 商品SKU 详情页服务
 * @data 2019/5/1
 * @vserion 1.0
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Value("${pagedir}")
    private String pagedir;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemCatalogMapper itemCatalogMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public boolean genItemHtml(Long goodsId) {
        //配置freemarkerm模板
        Configuration configuration = freeMarkerConfig.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            //模板数据
            Map<String, Object> dataModel = new HashMap<>();
            //加载商品SPU数据
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods",goods);
            //加载商品扩展信息
            GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc",goodsDesc);
            //商品分类
            String category1 = itemCatalogMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String category2 = itemCatalogMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String category3 = itemCatalogMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("category1",category1);
            dataModel.put("category2",category2);
            dataModel.put("category3",category3);
            //加载商品SKU列表
            ItemExample example = new ItemExample();
            ItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);
            criteria.andStatusEqualTo("1");
            //保证第一个为默认
            example.setOrderByClause("is_default desc");
            List<Item> itemList = itemMapper.selectByExample(example);
            dataModel.put("itemList",itemList);
            //生成静态文件
            Writer fileWriter = new FileWriter(pagedir+goodsId+".html");
            template.process(dataModel,fileWriter);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
