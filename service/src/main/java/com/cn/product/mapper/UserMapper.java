package com.cn.product.mapper;

import com.cn.product.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> pageQuery(User data);
}
