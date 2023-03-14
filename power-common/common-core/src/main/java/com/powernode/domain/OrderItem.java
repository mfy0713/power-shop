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

/**
    * 订单项
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "order_item")
public class OrderItem implements Serializable {
    /**
     * 订单项ID
     */
    @TableId(value = "order_item_id", type = IdType.AUTO)
    private Long orderItemId;

    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    private Long shopId;

    /**
     * 订单order_number
     */
    @TableField(value = "order_number")
    private String orderNumber;

    /**
     * 产品ID
     */
    @TableField(value = "prod_id")
    private Long prodId;

    /**
     * 产品SkuID
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 购物车产品个数
     */
    @TableField(value = "prod_count")
    private Integer prodCount;

    /**
     * 产品名称
     */
    @TableField(value = "prod_name")
    private String prodName;

    /**
     * sku名称
     */
    @TableField(value = "sku_name")
    private String skuName;

    /**
     * 产品主图片路径
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 产品价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 商品总金额
     */
    @TableField(value = "product_total_amount")
    private BigDecimal productTotalAmount;

    /**
     * 购物时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 评论状态： 0 未评价  1 已评价
     */
    @TableField(value = "comm_sts")
    private Integer commSts;

    private static final long serialVersionUID = 1L;
}