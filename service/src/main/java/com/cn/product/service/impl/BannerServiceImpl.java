package com.cn.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.product.entity.Banner;
import com.cn.product.mapper.BannerMapper;
import com.cn.product.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.product.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public List<Banner> getList() {
        return baseMapper.selectList(new QueryWrapper<Banner>().orderByAsc("create_time"));

    }

    @Override
    public PageResult pageQuery(int page, int limit, Banner data) {
        PageHelper.startPage(page, limit);
        List<Banner> queryList = baseMapper.pageQuery(data);
        PageInfo<Banner> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
