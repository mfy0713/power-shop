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
@TableName(value = "global_table")
public class GlobalTable implements Serializable {
    /**
     * 全局事务ID
     */
    @TableId(value = "xid", type = IdType.AUTO)
    private String xid;

    /**
     * 事务ID
     */
    @TableField(value = "transaction_id")
    private Long transactionId;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 应用ID
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * 事务分组名
     */
    @TableField(value = "transaction_service_group")
    private String transactionServiceGroup;

    /**
     * 执行事务的方法
     */
    @TableField(value = "transaction_name")
    private String transactionName;

    /**
     * 超时时间
     */
    @TableField(value = "timeout")
    private Integer timeout;

    /**
     * 开始时间
     */
    @TableField(value = "begin_time")
    private Long beginTime;

    /**
     * 应用数据
     */
    @TableField(value = "application_data")
    private String applicationData;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}