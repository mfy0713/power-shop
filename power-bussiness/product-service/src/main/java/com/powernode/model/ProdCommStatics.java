package com.powernode.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("商品评价统计")
public class ProdCommStatics {
    @ApiModelProperty("商品id")
    private Long prodId;
    @ApiModelProperty("好评数")
    private Integer goodCount;
    @ApiModelProperty("总评数")
    private Integer allCount;
}
