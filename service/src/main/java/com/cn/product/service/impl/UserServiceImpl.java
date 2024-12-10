package com.cn.product.service.impl;

import com.cn.product.entity.User;
import com.cn.product.entity.User;
import com.cn.product.mapper.UserMapper;
import com.cn.product.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.product.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult pageQuery(int page, int limit, User data) {
        PageHelper.startPage(page, limit);
        List<User> queryList = baseMapper.pageQuery(data);
        PageInfo<User> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
