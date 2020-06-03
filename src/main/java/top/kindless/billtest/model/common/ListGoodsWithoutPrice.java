package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListGoodsWithoutPrice extends CommonListGoods{

    @ApiModelProperty("关联的单据编号")
    private String refBillId;

    public ListGoodsWithoutPrice(Integer id,
                                 Integer goodsId,
                                 String commodityName,
                                 String specificationName,
                                 Integer amount,
                                 String refBillId){
        super(id, goodsId, commodityName, specificationName, amount);
        this.refBillId = refBillId;
    }
}
