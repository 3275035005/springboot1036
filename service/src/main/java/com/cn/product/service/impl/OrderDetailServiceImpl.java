package com.cn.product.service.impl;

import com.cn.product.entity.Order;
import com.cn.product.entity.OrderDetail;
import com.cn.product.mapper.OrderDetailMapper;
import com.cn.product.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.product.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细 服务实现类
 * </p>
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Override
    public PageResult pageQuery(int page, int limit, Order data) {
        PageHelper.startPage(page, limit);
        List<OrderDetail> queryList = baseMapper.pageQuery(data);
        PageInfo<OrderDetail> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Map<String, Object>> getZb() {
        return baseMapper.getZb();
    }
}
