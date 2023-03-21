package com.powernode.model;

import com.powernode.constant.EsConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting(shards = 3,replicas = 2,refreshInterval = "1s")    //分片数量3，副本2，刷新频率1s
@Document(indexName = EsConstant.ES_INDEX_NAME) //索引名称
@ApiModel("ES的商品实体类")
public class ProdES {

    @ApiModelProperty("商品id")
    @Id
    @Field(type= FieldType.Long)
    private Long prodId;

    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    @ApiModelProperty("商店名称")
    private String prodName;

    @Field(type= FieldType.Long)
    @ApiModelProperty("店铺id")
    private Long shopId;

    @Field(type = FieldType.Double)
    @ApiModelProperty("商店价格")
    private BigDecimal price;

    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    @ApiModelProperty("商店简要描述")
    private String brief;

    @ApiModelProperty("商品主图")
    private String pic;

    @ApiModelProperty("类别")
    private Long categoryId;

    @ApiModelProperty("销量")
    private Integer soldNum;

    @ApiModelProperty("库存")
    private Integer totalStocks;

    @Field(type = FieldType.Date)
    @ApiModelProperty("上架时间")
    private Date putawayTime;

    @ApiModelProperty("标签列表id")
    private List<Long> tagList;

    @Field(type = FieldType.Long)
    @ApiModelProperty("商品评论数")
    private Integer praiseNumber = 0;

    @Field(type = FieldType.Double)
    @ApiModelProperty("商品好评率")
    private BigDecimal positiveRating = BigDecimal.ZERO;

}
