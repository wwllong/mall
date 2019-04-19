package com.mall.mapper;

import com.mall.pojo.ItemCatalog;
import com.mall.pojo.ItemCatalogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCatalogMapper {
    long countByExample(ItemCatalogExample example);

    int deleteByExample(ItemCatalogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ItemCatalog record);

    int insertSelective(ItemCatalog record);

    List<ItemCatalog> selectByExample(ItemCatalogExample example);

    ItemCatalog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ItemCatalog record, @Param("example") ItemCatalogExample example);

    int updateByExample(@Param("record") ItemCatalog record, @Param("example") ItemCatalogExample example);

    int updateByPrimaryKeySelective(ItemCatalog record);

    int updateByPrimaryKey(ItemCatalog record);
}