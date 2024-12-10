package com.cn.product.service.impl;

import com.cn.product.entity.Message;
import com.cn.product.mapper.MessageMapper;
import com.cn.product.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.product.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 留言建议 服务实现类
 * </p>
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public PageResult pageQuery(int page, int limit, Message data) {
        PageHelper.startPage(page, limit);
        List<Message> queryList = baseMapper.pageQuery(data);
        PageInfo<Message> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Message> getMessageListByUserId(Integer userId) {
        return baseMapper.getMessageListByUserId(userId);
    }
}
