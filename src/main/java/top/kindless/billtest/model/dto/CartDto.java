package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonGoodsInfo;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto extends CommonGoodsInfo {

    @ApiModelProperty("购物车id")
    private Integer cartId;

    @ApiModelProperty("数量")
    private Integer amount;

    @ApiModelProperty("商品总价")
    private Float sumPrice;

    public CartDto(Integer goodsId,
                   Integer commodityId,
                   String commodityName,
                   Integer specificationId,
                   String specificationName,
                   Float price,
                   String imgUrl,
                   Integer cartId,
                   Integer amount,
                   Float sumPrice){
        super(goodsId, commodityId, commodityName, specificationId, specificationName, price, imgUrl);
        this.cartId = cartId;
        this.amount = amount;
        this.sumPrice = sumPrice;
    }

    public CartDto(Integer goodsId,
                   Integer commodityId,
                   String commodityName,
                   Integer specificationId,
                   String specificationName,
                   Float price,
                   String imgUrl,
                   Integer cartId,
                   Integer amount){
        super(goodsId, commodityId, commodityName, specificationId, specificationName, price, imgUrl);
        this.cartId = cartId;
        this.amount = amount;
        this.sumPrice = price * amount;
    }
}
