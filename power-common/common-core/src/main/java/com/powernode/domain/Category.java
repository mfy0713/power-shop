package com.powernode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 产品类目
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "category")
public class Category implements Serializable {
    /**
     * 类目ID
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    /**
     * 父节点
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 产品类目名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 类目图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 类目的显示图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 排序
     */
    @TableField(value = "seq")
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 记录时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}