package com.powernode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("评论总览")
public class ProdCommVo {
    @ApiModelProperty("商品id")
    private Long prodId;
    @ApiModelProperty("全部评论数")
    private Integer number;
    @ApiModelProperty("好评数")
    private Integer praiseNumber;
    @ApiModelProperty("中评数")
    private Integer secondaryNumber;
    @ApiModelProperty("差评数")
    private Integer negativeNumber;
    @ApiModelProperty("有图评论数")
    private Integer picNumber;

    @ApiModelProperty("好评率")
    private BigDecimal positiveRating;

}
