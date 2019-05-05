package com.mall.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.user.service.UserService;
import com.mall.mapper.UserMapper;
import com.mall.pojo.User;
import com.mall.pojo.UserExample;
import com.mall.pojo.UserExample.Criteria;
import com.mall.utils.StringUtils;
import common.pojo.PageResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现
 * @author Wwl
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination smsDestination;

	@Value("${sign_name}")
	private String sign_name;

	@Value("${template_code}")
	private String template_code;

	@Override
	public void add(User user) {
		Date data = new Date();
		user.setCreated(data);
		user.setUpdated(data);
		user.setSourceType("1");
		String password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(password);
		userMapper.insert(user);
	}

	@Override
	public void update(User user) {
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public User findOne(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> findAll() {
		return userMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		Page<User> page = (Page<User>) userMapper.selectByExample(null);

		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public PageResult findPage(User user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		if(user!=null){

			if(!StringUtils.isEmpty(user.getUsername())){
				criteria.andUsernameLike("%"+user.getUsername()+"%");
			}
			if(!StringUtils.isEmpty(user.getPassword())){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(!StringUtils.isEmpty(user.getPhone())){
				criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(!StringUtils.isEmpty(user.getEmail())){
				criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(!StringUtils.isEmpty(user.getSourceType())){
				criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
			}
			if(!StringUtils.isEmpty(user.getNickName())){
				criteria.andNickNameLike("%"+user.getNickName()+"%");
			}
			if(!StringUtils.isEmpty(user.getName())){
				criteria.andNameLike("%"+user.getName()+"%");
			}
			if(!StringUtils.isEmpty(user.getStatus())){
				criteria.andStatusLike("%"+user.getStatus()+"%");
			}
			if(!StringUtils.isEmpty(user.getHeadPic())){
				criteria.andHeadPicLike("%"+user.getHeadPic()+"%");
			}
			if(!StringUtils.isEmpty(user.getQq())){
				criteria.andQqLike("%"+user.getQq()+"%");
			}
			if(!StringUtils.isEmpty(user.getIsMobileCheck())){
				criteria.andIsMobileCheckLike("%"+user.getIsMobileCheck()+"%");
			}
			if(!StringUtils.isEmpty(user.getIsEmailCheck())){
				criteria.andIsEmailCheckLike("%"+user.getIsEmailCheck()+"%");
			}
			if(!StringUtils.isEmpty(user.getSex())){
				criteria.andSexLike("%"+user.getSex()+"%");
			}
		}
		Page<User> page = (Page<User>)userMapper.selectByExample(example);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void delete(Long[] ids) {
		for(Long id : ids){
			userMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public User findByUsername(String username) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> userList = userMapper.selectByExample(example);
		if(userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public void createSmsCode(String phone) {
		//生成随机六位数
		String code = String.valueOf(((int)((Math.random()*9+1)*100000)));
		//存储到Redis
		redisTemplate.boundHashOps("smsCode").put(phone,code);
		System.out.println(code);
		//发送到activeMQ
		jmsTemplate.send(smsDestination, session -> {
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("sign_name",sign_name);
			mapMessage.setString("template_code",template_code);
			mapMessage.setString("phone_numbers",phone);
			Map paramMap = new HashMap<>();
			paramMap.put("code",code);
			mapMessage.setString("template_param", JSON.toJSONString(paramMap));
			return mapMessage;
		});
	}

	@Override
	public boolean checkSmsCode(String phone, String code) {
		//获取缓存验证码
		String smsCode = (String)redisTemplate.boundHashOps("smsCode").get(phone);
		if(smsCode==null){
			return false;
		}
		if(!smsCode.equals(code)){
			return false;
		}
		return true;
	}

}
