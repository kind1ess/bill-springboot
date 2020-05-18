package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonGoodsInfo {

    @ApiModelProperty("货物id")
    private Integer goodsId;

    @ApiModelProperty("商品id")
    private Integer commodityId;

    @ApiModelProperty("商品名字")
    private String commodityName;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("规格名字")
    private String specificationName;

    @ApiModelProperty("商品价格")
    private Float price;

    @ApiModelProperty("商品图片")
    private String imgUrl;
}
