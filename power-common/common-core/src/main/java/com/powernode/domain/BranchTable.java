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
@TableName(value = "branch_table")
public class BranchTable implements Serializable {
    /**
     * 分支事务ID
     */
    @TableId(value = "branch_id", type = IdType.AUTO)
    private Long branchId;

    /**
     * 全局事务ID
     */
    @TableField(value = "xid")
    private String xid;

    /**
     * 全局事务ID，不带TC地址
     */
    @TableField(value = "transaction_id")
    private Long transactionId;

    /**
     * 资源分组ID
     */
    @TableField(value = "resource_group_id")
    private String resourceGroupId;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    private String resourceId;

    /**
     * 事务模式，AT、XA等
     */
    @TableField(value = "branch_type")
    private String branchType;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 客户端ID
     */
    @TableField(value = "client_id")
    private String clientId;

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