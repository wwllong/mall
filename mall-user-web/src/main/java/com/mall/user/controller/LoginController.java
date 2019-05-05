package com.mall.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Wen
 * @className LoginController
 * @dsecription 登陆控制器
 * @data 2019/5/5
 * @vserion 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/getUser")
    public Map<String,Object> getLoginUser(){
        Map<String,Object> result = new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        result.put("name",name);
        return result;
    }


}
