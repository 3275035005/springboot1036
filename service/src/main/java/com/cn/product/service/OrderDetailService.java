package com.cn.product.service;

import com.cn.product.entity.Order;
import com.cn.product.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细 服务类
 * </p>
 */
public interface OrderDetailService extends IService<OrderDetail> {

    PageResult pageQuery(int page, int limit, Order data);

    List<Map<String, Object>> getZb();


}
