package com.powernode.dto;

import com.powernode.domain.Prod;
import com.powernode.domain.Sku;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("增加商品的实体类")
public class ProdDto extends Prod {

    @ApiModelProperty("sku集合")
    private List<Sku> skuList;

    @ApiModelProperty("标签集合")
    private List<Long> tagList;

    @ApiModelProperty("配送模式")
    private DeliveryMode deliveryModeVo;

    public static class DeliveryMode{
        private boolean hasShopDelivery;
        private boolean hasUserPickUp;
    }
}
