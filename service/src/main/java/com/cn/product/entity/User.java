package com.cn.product.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user`")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 角色（1管理员 2项目负责人 3用户）
     */
    private String role;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @TableField(exist = false)
    private String[] roles;



}
