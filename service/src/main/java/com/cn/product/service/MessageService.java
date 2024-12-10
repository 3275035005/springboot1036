package com.cn.product.service;

import com.cn.product.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.product.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 留言建议 服务类
 * </p>
 */
public interface MessageService extends IService<Message> {

    PageResult pageQuery(int page, int limit, Message data);

    /**
     * 通过用户id查询留言建议
     * @param userId
     * @return
     */
    List<Message> getMessageListByUserId(Integer userId);

}
