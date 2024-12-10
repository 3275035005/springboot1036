package com.cn.product.service;

import com.cn.product.entity.Materials;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 原材料 服务类
 * </p>
 */
public interface MaterialsService extends IService<Materials> {

    PageResult pageQuery(int page, int limit, Materials data);

    List<Map<String, Object>> getZb();


}
