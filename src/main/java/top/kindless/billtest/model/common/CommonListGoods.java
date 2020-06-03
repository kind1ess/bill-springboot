package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonListGoods {

    @ApiModelProperty("明细id")
    private Integer id;

    @ApiModelProperty("产品编号")
    private Integer goodsId;

    @ApiModelProperty("产品名称")
    private String commodityName;

    @ApiModelProperty("产品规格")
    private String specificationName;

    @ApiModelProperty("单位")
    private final String unit = "辆";

    @ApiModelProperty("产品数量")
    private Integer amount;
}
