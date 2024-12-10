package com.cn.product.service;

import com.cn.product.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 */
public interface OrderService extends IService<Order> {

    PageResult pageQuery(int page, int limit, Order data);

    List<Order> getOrder(Integer id);

    List<Map<String, Object>> getZzt(String thisMonth, int size);
}
