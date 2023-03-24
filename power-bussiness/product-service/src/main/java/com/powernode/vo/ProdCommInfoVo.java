package com.powernode.vo;

import com.powernode.domain.ProdComm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("评论详情vo实体")
public class ProdCommInfoVo extends ProdComm {

    @ApiModelProperty("评论人的昵称")
    private String nickName;
    @ApiModelProperty("评论人的头像")
    private String pic;

}
