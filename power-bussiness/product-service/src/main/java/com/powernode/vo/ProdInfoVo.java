package com.powernode.vo;

import com.powernode.domain.Prod;
import com.powernode.domain.Sku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("商品详情的实体类")
public class ProdInfoVo extends Prod {
    @ApiModelProperty("sku集合属性")
    private List<Sku> skuList;
}
