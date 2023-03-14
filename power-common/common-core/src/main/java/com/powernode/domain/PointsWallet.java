package com.powernode.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "points_wallet")
public class PointsWallet implements Serializable {
    /**
     * 积分钱包id
     */
    @TableId(value = "points_wallet_id", type = IdType.AUTO)
    private Long pointsWalletId;

    /**
     * 积分Id
     */
    @TableField(value = "points_id")
    private Long pointsId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 待结算积分
     */
    @TableField(value = "unsettled")
    private Double unsettled;

    /**
     * 已结算积分
     */
    @TableField(value = "settled")
    private Double settled;

    /**
     * 积累收益积分
     */
    @TableField(value = "addup")
    private Double addup;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    private Integer version;

    private static final long serialVersionUID = 1L;
}