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
@TableName(value = "message")
public class Message implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 留言创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 联系方式
     */
    @TableField(value = "contact")
    private String contact;

    /**
     * 留言内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 留言回复
     */
    @TableField(value = "reply")
    private String reply;

    /**
     * 状态：0:未审核 1审核通过
     */
    @TableField(value = "`status`")
    private Byte status;

    private static final long serialVersionUID = 1L;
}