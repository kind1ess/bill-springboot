package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllReserveAmount {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品名称")
    private String commodityName;

    @ApiModelProperty("商品规格")
    private String specificationName;

    @ApiModelProperty("单位")
    private final String unit = "辆";

    @ApiModelProperty("出库数量")
    private Long odoAmount;

    @ApiModelProperty("入库数量")
    private Long enterAmount;

    @ApiModelProperty("库存总剩余量")
    private Long allAmount;
}
