package com.mall.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Wen
 * @className LoginController
 * @dsecription 登陆相关
 * @data 2019/4/17
 * @vserion 1.0
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 登陆用户的名称
     * @return
     */
    @RequestMapping("/name")
    public Map name(){
        Map<String,Object> map = new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("loginName",name);
        return map;

     }
}
