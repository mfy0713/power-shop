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
    * 定时任务日志
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "schedule_job_log")
public class ScheduleJobLog implements Serializable {
    /**
     * 任务日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 任务id
     */
    @TableField(value = "job_id")
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
     * 任务状态    0：成功    1：失败
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 失败信息
     */
    @TableField(value = "error")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @TableField(value = "times")
    private Integer times;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}