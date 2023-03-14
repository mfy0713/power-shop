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
    * 品牌表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "brand")
public class Brand implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;

    /**
     * 品牌名称
     */
    @TableField(value = "brand_name")
    private String brandName;

    /**
     * 图片路径
     */
    @TableField(value = "brand_pic")
    private String brandPic;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 备注
     */
    @TableField(value = "memo")
    private String memo;

    /**
     * 顺序
     */
    @TableField(value = "seq")
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 简要描述
     */
    @TableField(value = "brief")
    private String brief;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 记录时间
     */
    @TableField(value = "rec_time")
    private Date recTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 品牌首字母
     */
    @TableField(value = "first_char")
    private String firstChar;

    private static final long serialVersionUID = 1L;
}