package com.mall.user.controller;

import com.mall.user.service.UserService;
import com.mall.pojo.User;
import com.mall.utils.PhoneFormatCheckUtils;
import common.pojo.PageResult;
import common.pojo.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制层
 *
 * @author Wwl
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 新增
     *
     * @param user 用户信息，code 验证码
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody User user,String code) {
        if(user.getUsername()!=null){
            if(userService.findByUsername(user.getUsername())!=null){
                return Result.error("该用户名已存在!");
            }
        }
        boolean check = userService.checkSmsCode(user.getPhone(), code);
        if(check==false){
            return Result.error("验证码错误!");
        }
        try {
            userService.add(user);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }

    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody User user) {
        try {
            userService.update(user);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 查找一条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public User findOne(Long id) {
        return userService.findOne(id);
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 列表查询-分页
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/listByPage")
    public PageResult findPage(int page, int size) {
        return userService.findPage(page, size);
    }

    /**
     * 条件查询-分页
     *
     * @param user
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody User user, int page, int size) {
        return userService.findPage(user, page, size);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            userService.delete(ids);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping("/sendCode")
    public Result sendCode(String phone) {
        //判断手机格式
        if(!PhoneFormatCheckUtils.isPhoneLegal(phone)){
            return Result.error("手机格式不正确!");
        }
        try {
            userService.createSmsCode(phone);
            return Result.ADMIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}