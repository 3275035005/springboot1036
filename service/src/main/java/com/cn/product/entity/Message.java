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
 * 留言建议
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言用户id
     */
    private Integer userId;

    /**
     * 回复内容
     */
    private String fcontent;

    /**
     * 回复用户id
     */
    private Integer fuserId;

    /**
     * 留言时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 回复状态（0未回复 1已回复）
     */
    private String state;

    /**
     * 留言用户姓名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 回复用户姓名
     */
    @TableField(exist = false)
    private String fuserName;

}
