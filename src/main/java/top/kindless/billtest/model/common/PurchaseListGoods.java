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
public class PurchaseListGoods extends ListGoods{
    @ApiModelProperty("关联的单据编号")
    private String refBillId;

    public PurchaseListGoods(Integer id,
                             Integer goodsId,
                             String commodityName,
                             String specificationName,
                             Integer amount,
                             Float price,
                             String refBillId){
        super(id, goodsId, commodityName, specificationName, amount, price);
        this.refBillId = refBillId;
    }
}
