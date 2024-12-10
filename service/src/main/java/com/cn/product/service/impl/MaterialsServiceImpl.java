package com.cn.product.service.impl;

import com.cn.product.entity.Materials;
import com.cn.product.entity.Materials;
import com.cn.product.mapper.MaterialsMapper;
import com.cn.product.service.MaterialsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.product.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 原材料 服务实现类
 * </p>
 */
@Service
public class MaterialsServiceImpl extends ServiceImpl<MaterialsMapper, Materials> implements MaterialsService {

    @Override
    public PageResult pageQuery(int page, int limit, Materials data) {
        PageHelper.startPage(page, limit);
        List<Materials> queryList = baseMapper.pageQuery(data);
        PageInfo<Materials> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Map<String, Object>> getZb() {
        return baseMapper.getZb();
    }
}
