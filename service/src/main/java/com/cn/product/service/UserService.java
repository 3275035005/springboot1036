package com.cn.product.service;

import com.cn.product.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface UserService extends IService<User> {

    PageResult pageQuery(int page, int limit, User data);
}
