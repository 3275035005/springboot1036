package com.cn.product.mapper;

import com.cn.product.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> pageQuery(Order data);

    List<Order> getOrder(@Param("id") Integer id);

    List<Map<String, Object>> getZzt(@Param("thisMonth")String thisMonth,@Param("stringSize") int stringSize, @Param("size") int size);
}
