package com.cn.product.mapper;

import com.cn.product.entity.Order;
import com.cn.product.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细 Mapper 接口
 * </p>
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    List<OrderDetail> pageQuery(Order data);

    List<Map<String, Object>> getZb();

}
