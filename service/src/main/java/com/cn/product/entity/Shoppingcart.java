package com.cn.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购物车
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Shoppingcart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 电子零件id
     */
    private Integer productId;

    /**
     * 加入数量
     */
    private Integer num;

    /**
     * 库存
     */
    @TableField(exist = false)
    private Integer maxNum;


    /**
     * 单价
     */
    @TableField(exist = false)
    private Double price;


    /**
     * 电子零件封面
     */
    @TableField(exist = false)
    private String productImg;

    /**
     * 电子零件名称
     */
    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private boolean check;
}
