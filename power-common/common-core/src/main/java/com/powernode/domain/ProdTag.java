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
    * 商品分组表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "prod_tag")
public class ProdTag implements Serializable {
    /**
     * 分组标签id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 状态(1为正常,0为删除)
     */
    @TableField(value = "`status`")
    private Boolean status;

    /**
     * 列表样式(0:一列一个,1:一列两个,2:一列三个)
     */
    @TableField(value = "`style`")
    private Integer style;

    /**
     * 排序
     */
    @TableField(value = "seq")
    private Integer seq;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}