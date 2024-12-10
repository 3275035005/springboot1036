package com.cn.product.mapper;

import com.cn.product.entity.Materials;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 原材料 Mapper 接口
 * </p>
 */
public interface MaterialsMapper extends BaseMapper<Materials> {

    List<Materials> pageQuery(Materials data);

    List<Map<String, Object>> getZb();

}
