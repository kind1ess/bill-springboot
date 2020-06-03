package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 清单每一行货物信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ListGoods extends CommonListGoods{

    @ApiModelProperty("产品单价")
    private Float price;

    @ApiModelProperty("小计")
    private Float sumPrice;

    public ListGoods(Integer id,Integer goodsId,String commodityName,String specificationName,Integer amount,Float price,Float sumPrice){
        super(id, goodsId, commodityName, specificationName, amount);
        this.price = price;
        this.sumPrice = sumPrice;
    }

    public ListGoods(Integer id,Integer goodsId,String commodityName,String specificationName,Integer amount,Float price){
        super(id, goodsId, commodityName, specificationName, amount);
        this.price = price;
        this.sumPrice = price * amount;
    }
}
