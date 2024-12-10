package com.cn.product.service;

import com.cn.product.entity.Information;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 新闻 服务类
 * </p>
 */
public interface InformationService extends IService<Information> {

    PageResult pageQuery(int page, int limit, Information data);

    /**
     * 查询新闻列表
     * @return
     */
    List<Information> getList();

}
