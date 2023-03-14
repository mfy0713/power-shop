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
@TableName(value = "attach_file")
public class AttachFile implements Serializable {
    @TableId(value = "file_id", type = IdType.AUTO)
    private Long fileId;

    /**
     * 文件路径
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Integer fileSize;

    /**
     * 上传时间
     */
    @TableField(value = "upload_time")
    private Date uploadTime;

    /**
     * 文件关联的表主键id
     */
    @TableField(value = "file_join_id")
    private Long fileJoinId;

    /**
     * 文件关联表类型：1 商品表  FileJoinType
     */
    @TableField(value = "file_join_type")
    private Byte fileJoinType;

    private static final long serialVersionUID = 1L;
}