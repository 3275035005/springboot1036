package com.cn.product.service;

import com.cn.product.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 */
public interface BannerService extends IService<Banner> {

    /**
     * 查询轮播图列表
     * @return
     */
    List<Banner> getList();


    /**
     * 分页查询数据
     * @param page
     * @param limit
     * @param data
     * @return
     */
    PageResult pageQuery(int page, int limit, Banner data);
}
