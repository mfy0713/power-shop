package com.powernode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "points_prod")
public class PointsProd implements Serializable {
    /**
     * 积分商品id
     */
    @TableId(value = "points_prod_id", type = IdType.AUTO)
    private Long pointsProdId;

    /**
     * 所需积分id
     */
    @TableField(value = "points_id")
    private Long pointsId;

    /**
     * 所需积分量
     */
    @TableField(value = "points_number")
    private Double pointsNumber;

    /**
     * 所需金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 关联商品id
     */
    @TableField(value = "prod_id")
    private Long prodId;

    /**
     * 库存
     */
    @TableField(value = "stocks")
    private Integer stocks;

    /**
     * 状态(0下架 1上架)
     */
    @TableField(value = "`state`")
    private Byte state;

    /**
     * 上架时间
     */
    @TableField(value = "upper_shelf_time")
    private Date upperShelfTime;

    /**
     * 下架时间
     */
    @TableField(value = "lower_shelf")
    private Date lowerShelf;

    private static final long serialVersionUID = 1L;
}