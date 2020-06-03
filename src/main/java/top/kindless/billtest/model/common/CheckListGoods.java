package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CheckListGoods extends ListGoodsWithoutPrice{

    @ApiModelProperty("实际数量")
    private Integer actualAmount;

    @ApiModelProperty("备注")
    private String remark;

    public CheckListGoods(Integer id,
                          Integer goodsId,
                          String commodityName,
                          String specificationName,
                          Integer amount,
                          String refBillId,
                          Integer actualAmount,
                          String remark){
        super(id, goodsId, commodityName, specificationName, amount, refBillId);
        this.actualAmount = actualAmount;
        this.remark = remark;
    }
}
