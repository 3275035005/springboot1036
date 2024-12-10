package com.cn.product.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单明细
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 电子零件id
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer productNum;

    /**
     * 单价
     */
    private Double productPrice;

    /**
     * 加工状态 0未开始 1加工中 2加工完成
     */
    private String status;

    /**
     * 已完工数量
     */
    private Integer number;

    /**
     * 完工时间
     */
    private Date accomplishTime;

    /**
     * 预计完工时间
     */
    private String predictTime;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private String productPicture;

    @TableField(exist = false)
    private Long orderNo;
}
