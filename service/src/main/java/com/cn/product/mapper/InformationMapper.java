package com.cn.product.mapper;

import com.cn.product.entity.Information;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 新闻 Mapper 接口
 * </p>
 */
public interface InformationMapper extends BaseMapper<Information> {

    List<Information> pageQuery(Information data);

}
