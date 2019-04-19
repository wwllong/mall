package com.mall.service;

import com.mall.goods.service.SellerService;
import com.mall.pojo.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Wen
 * @className UserDetailsServiceImpl
 * @dsecription 自定义认证类
 * @data 2019/4/18
 * @vserion 1.0
 */

public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    /**
     * 构建角色列表
     */
    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(){
        private static final long serialVersionUID = 1L;
        {
            add(new SimpleGrantedAuthority("ROLE_SELLER"));
        }
    };

    /**
     * 用户验证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Seller seller = sellerService.findOne(username);
        if(seller==null){
            throw new UsernameNotFoundException("用户不存在");
        }else{
            //判断商户有无启用
            String ENABLE = "1";
            if(ENABLE.equals(seller.getStatus())){
                return new User(username,seller.getPassword(),AUTHORITIES);
            }else{
                throw new UsernameNotFoundException("用户被禁用");
            }
        }
    }
}
