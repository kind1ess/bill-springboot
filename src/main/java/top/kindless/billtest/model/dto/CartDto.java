package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    @ApiModelProperty("购物车id")
    private Integer cartId;

    @ApiModelProperty("货物id")
    private Integer goodsId;

    @ApiModelProperty("商品id")
    private Integer commodityId;

    @ApiModelProperty("规格id")
    private Integer specificationId;

    @ApiModelProperty("商品名称")
    private String commodityName;

    @ApiModelProperty("规格名称")
    private String specificationName;

    @ApiModelProperty("商品单价")
    private Float price;

    @ApiModelProperty("数量")
    private Integer amount;

    @ApiModelProperty("商品总价")
    private Float sumPrice;
}
