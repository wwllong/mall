package com.mall.page.service;

/**
 * @author Jack Wen
 * @className ItemPageService
 * @dsecription 商品详细页接口
 * @data 2019/5/1 0001
 * @vserion 1.0
 */

public interface ItemPageService {

    /**
     * 生成商品详细页
     * @param goodsId 商品SPU Id
     */
    public boolean genItemHtml(Long goodsId);

    /**
     * 删除商品详细页
     * @param goodsId 商品SPU Id
     * @return
     */
    public boolean deleteItemHtml(Long goodsId);

}
