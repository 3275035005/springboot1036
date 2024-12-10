package com.cn.product.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 项目经理
     */
    private String processUserId;

    /**
     * 订单状态(0未开始 1加工中 2加工完成 3订单已发货 4订单完成)
     */
    private String status;


    /**
     * 完工日期
     */
    private Date complete;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 下单用户
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 项目经理
     */
    @TableField(exist = false)
    private String processUserName;

    @TableField(exist = false)
    List<OrderDetail> orderList;
}
