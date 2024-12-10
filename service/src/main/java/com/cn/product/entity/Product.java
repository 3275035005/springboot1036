package com.cn.product.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 电子零件表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 电子零件名称
     */
    private String productName;


    /**
     * 零件号
     */
    private String partNumber;

    /**
     * 电子零件分类id
     */
    private Integer categoryId;

    /**
     * 电子零件标题
     */
    private String productTitle;

    /**
     * 封面
     */
    private String productPicture;

    /**
     * 原价
     */
    private Double productPrice;

    /**
     * 现价
     */
    private Double productSellingPrice;

    /**
     * 销量
     */
    private Integer productSales;
     /**
     * 类型(0原价电子零件 1打折电子零件 2试吃电子零件)
     */
    private String type;

    /**
     * 所需原材料
     */
    private String materials;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private List<String> imageList;

    /**
     * 类别名称
     */
    @TableField(exist = false)
    private String categoryName;

}
