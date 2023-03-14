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
@TableName(value = "t_tx_exception")
public class TTxException implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "ex_state")
    private Short exState;

    @TableField(value = "group_id")
    private String groupId;

    @TableField(value = "mod_id")
    private String modId;

    @TableField(value = "registrar")
    private Short registrar;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "transaction_state")
    private Integer transactionState;

    @TableField(value = "unit_id")
    private String unitId;

    private static final long serialVersionUID = 1L;
}