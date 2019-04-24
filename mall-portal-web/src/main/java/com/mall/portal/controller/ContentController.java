package com.mall.portal.controller;

import com.mall.content.service.ContentService;
import com.mall.pojo.Content;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jack Wen
 * @className ContentController
 * @dsecription 内容控制器
 * @data 2019/4/24 0024
 * @vserion 1.0
 */

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }
}
