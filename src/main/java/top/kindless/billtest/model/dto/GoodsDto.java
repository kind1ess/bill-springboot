package top.kindless.billtest.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonGoodsInfo;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDto extends CommonGoodsInfo {

    @ApiModelProperty("商品库存")
    private Integer amount;

    public GoodsDto(Integer goodsId,
                    Integer commodityId,
                    String commodityName,
                    Integer specificationId,
                    String specificationName,
                    Float price,
                    String imgUrl,
                    Integer amount){
        super(goodsId, commodityId, commodityName, specificationId, specificationName, price, imgUrl);
        this.amount = amount;
    }
}
