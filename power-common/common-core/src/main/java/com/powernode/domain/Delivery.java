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
    * 物流公司
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "delivery")
public class Delivery implements Serializable {
    /**
     * ID
     */
    @TableId(value = "dvy_id", type = IdType.AUTO)
    private Long dvyId;

    /**
     * 物流公司名称
     */
    @TableField(value = "dvy_name")
    private String dvyName;

    /**
     * 公司主页
     */
    @TableField(value = "company_home_url")
    private String companyHomeUrl;

    /**
     * 建立时间
     */
    @TableField(value = "rec_time")
    private Date recTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    private Date modifyTime;

    /**
     * 物流查询接口
     */
    @TableField(value = "query_url")
    private String queryUrl;

    private static final long serialVersionUID = 1L;
}