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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "score_log")
public class ScoreLog implements Serializable {
    /**
     * 积分记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 0支出 1收入
     */
    @TableField(value = "`type`")
    private Byte type;

    /**
     * 记录创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 流水号
     */
    @TableField(value = "sn")
    private String sn;

    /**
     * 积分类型：1回收加积分 2购买减积分
     */
    @TableField(value = "score_type")
    private Byte scoreType;

    private static final long serialVersionUID = 1L;
}