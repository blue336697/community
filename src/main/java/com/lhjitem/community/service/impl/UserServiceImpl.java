package com.lhjitem.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhjitem.community.mapper.UserMapper;
import com.lhjitem.community.model.User;
import com.lhjitem.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhj
 * @create 2022/2/20 19:34
 * 来解决用户重复创建以及不一致的问题
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("account_id", user.getAccountId());
        User checkUser = userMapper.selectOne(userQueryWrapper);
//        User checkUser = userMapper.findByAccountId(user.getAccountId());
        if(checkUser == null){
            //如果没有这个用户就新建这个
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //有就更新token
            checkUser.setGmtModified(System.currentTimeMillis());
            checkUser.setAvatarUrl(user.getAvatarUrl());
            checkUser.setName(user.getName());
            checkUser.setToken(user.getToken());
            userMapper.updateById(checkUser);
        }
    }
}
