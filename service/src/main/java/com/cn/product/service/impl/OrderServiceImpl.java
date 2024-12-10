package com.cn.product.service.impl;

import com.cn.product.entity.Order;
import com.cn.product.entity.Order;
import com.cn.product.mapper.OrderMapper;
import com.cn.product.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.product.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public PageResult pageQuery(int page, int limit, Order data) {
        PageHelper.startPage(page, limit);
        List<Order> queryList = baseMapper.pageQuery(data);
        PageInfo<Order> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Order> getOrder(Integer id) {
        return baseMapper.getOrder(id);
    }

    @Override
    public List<Map<String, Object>> getZzt(String thisMonth, int size) {
        return baseMapper.getZzt(thisMonth,thisMonth.length(), size);
    }
}
