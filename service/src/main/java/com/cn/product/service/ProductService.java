package com.cn.product.service;

import com.cn.product.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 电子零件表 服务类
 * </p>
 */
public interface ProductService extends IService<Product> {

    PageResult pageQuery(int page, int limit, Product data);

    List<Product> getList(Product setType);

    Product getListById(Integer id);
}
