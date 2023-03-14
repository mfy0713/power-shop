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
@TableName(value = "shop_detail")
public class ShopDetail implements Serializable {
    /**
     * 店铺id
     */
    @TableId(value = "shop_id", type = IdType.AUTO)
    private Long shopId;

    /**
     * 店铺名称(数字、中文，英文(可混合，不可有特殊字符)，可修改)、不唯一
     */
    @TableField(value = "shop_name")
    private String shopName;

    /**
     * 店长用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 店铺类型
     */
    @TableField(value = "shop_type")
    private Byte shopType;

    /**
     * 店铺简介(可修改)
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 店铺公告(可修改)
     */
    @TableField(value = "shop_notice")
    private String shopNotice;

    /**
     * 店铺行业(餐饮、生鲜果蔬、鲜花等)
     */
    @TableField(value = "shop_industry")
    private Byte shopIndustry;

    /**
     * 店长
     */
    @TableField(value = "shop_owner")
    private String shopOwner;

    /**
     * 店铺绑定的手机(登录账号：唯一)
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 店铺联系电话
     */
    @TableField(value = "tel")
    private String tel;

    /**
     * 店铺所在纬度(可修改)
     */
    @TableField(value = "shop_lat")
    private String shopLat;

    /**
     * 店铺所在经度(可修改)
     */
    @TableField(value = "shop_lng")
    private String shopLng;

    /**
     * 店铺详细地址
     */
    @TableField(value = "shop_address")
    private String shopAddress;

    /**
     * 店铺所在省份（描述）
     */
    @TableField(value = "province")
    private String province;

    /**
     * 店铺所在城市（描述）
     */
    @TableField(value = "city")
    private String city;

    /**
     * 店铺所在区域（描述）
     */
    @TableField(value = "area")
    private String area;

    /**
     * 店铺省市区代码，用于回显
     */
    @TableField(value = "pca_code")
    private String pcaCode;

    /**
     * 店铺logo(可修改)
     */
    @TableField(value = "shop_logo")
    private String shopLogo;

    /**
     * 店铺相册
     */
    @TableField(value = "shop_photos")
    private String shopPhotos;

    /**
     * 每天营业时间段(可修改)
     */
    @TableField(value = "open_time")
    private String openTime;

    /**
     * 店铺状态(-1:未开通 0: 停业中 1:营业中)，可修改
     */
    @TableField(value = "shop_status")
    private Byte shopStatus;

    /**
     * 0:商家承担运费; 1:买家承担运费
     */
    @TableField(value = "transport_type")
    private Byte transportType;

    /**
     * 固定运费
     */
    @TableField(value = "fixed_freight")
    private BigDecimal fixedFreight;

    /**
     * 满X包邮
     */
    @TableField(value = "full_free_shipping")
    private BigDecimal fullFreeShipping;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 分销开关(0:开启 1:关闭)
     */
    @TableField(value = "is_distribution")
    private Byte isDistribution;

    private static final long serialVersionUID = 1L;
}