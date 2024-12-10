package com.cn.product.mapper;

import com.cn.product.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 留言建议 Mapper 接口
 * </p>
 */
public interface MessageMapper extends BaseMapper<Message> {

    List<Message> pageQuery(Message data);

    List<Message> getMessageListByUserId(@Param("userId") Integer userId);
}
