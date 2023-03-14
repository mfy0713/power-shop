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
    * 定时任务
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "schedule_job")
public class ScheduleJob implements Serializable {
    /**
     * 任务id
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    /**
     * spring bean名称
     */
    @TableField(value = "bean_name")
    private String beanName;

    /**
     * 方法名
     */
    @TableField(value = "method_name")
    private String methodName;

    /**
     * 参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}